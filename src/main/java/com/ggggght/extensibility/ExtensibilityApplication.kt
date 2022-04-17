package com.ggggght.extensibility

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.aop.SpringProxy
import org.springframework.aop.framework.Advised
import org.springframework.aop.framework.ProxyFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.DecoratingProxy
import org.springframework.nativex.hint.JdkProxyHint
import org.springframework.nativex.hint.NativeHint

@NativeHint(jdkProxies = [JdkProxyHint(types = [CustomerService::class, SpringProxy::class, Advised::class, DecoratingProxy::class])])
@SpringBootApplication
open class ExtensibilityApplication {
  @Bean open fun customerService(): CustomerService {
    val proxy = ProxyFactory.getProxy(
      CustomerService::class.java,
      MethodInterceptor label@{ invocation: MethodInvocation ->
        if (invocation.method.name == "findById") {
          return@label Customer(1,"GHT")
        }
        null
      })

    for (clazz in proxy.javaClass.interfaces) {
      println(clazz.name)
    }
    return proxy
  }

  @Bean open fun applicationRunner(customerService: CustomerService): ApplicationRunner {
    return ApplicationRunner { println(customerService.findById(1)) }
  }

  companion object {
    @JvmStatic fun main(args: Array<String>) {
      SpringApplication.run(ExtensibilityApplication::class.java, *args)
    }
  }
}