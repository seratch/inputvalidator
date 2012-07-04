package com.github.seratch

import ru.circumflex._
import ru.circumflex.core._
import ru.circumflex.web._

import org.fusesource.scalate.{RenderContext, TemplateEngine}
import org.fusesource.scalate.servlet._

trait ScalateSupport {

  object ScalateConfig extends Config {
    def getServletContext = servletContext
    def getInitParameter(name: String) = getServletContext.getInitParameter(name)
    def getInitParameterNames = getServletContext.getInitParameterNames
    def getName = getServletContext.getServletContextName
  }

  val engine = new ServletTemplateEngine(ScalateConfig)

  def renderContext(path: String, attributeMap: Map[String, Any] = Map()): RenderContext = {
    val context = new ServletRenderContext(engine, request.raw, response.raw, servletContext)
    attributeMap.foreach{case (k, v) => context.attributes(k) = v}
    context
  }

  def layout(path: String, attributeMap: Map[String, Any] = Map()): Nothing = {
    using { () =>
      val templateEngine = engine.load(path, Nil)
      engine.layout(templateEngine, renderContext(path, attributeMap))
    }
  }

  def render(path: String, attributeMap: Map[String, Any] = Map()): Nothing = {
    using { () =>
    val context = new ServletRenderContext(engine, request.raw, response.raw, servletContext)
    context.render(path, attributeMap) }
  }

  def using[A](f: () => A): Nothing = {
    f()
    response.flush()
  }

}

