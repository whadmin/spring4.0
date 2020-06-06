/**
 * @Author: wuhao.w
 * @Date: 2020/6/6 16:35
 *
 * profile 表示按照特定环境进行装配
 *
 * 我们在平常开发中会存在不同环境配置，如dev，produce等,不同环境的配置也会不同
 *
 * Spring.profile可以帮助我们对Bean进行打标签，例如对于特定的Bean只有在特定环境中才会生效
 * 这样我们可以通过定义多个Bean来读取不同环境的配置，并将Bean打上标签。方便切换
 *
 * 需要注意的是，特别环境Bean如果和没有环境Bean名称相同，在使用特别环境时，打上标签的Bean定义会覆盖没有标签的Bean
 *
 */
package com.spring.ioc.bean.assemblyBean.testRuning.profile;