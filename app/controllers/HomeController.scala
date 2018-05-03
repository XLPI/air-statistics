package controllers

import javax.inject.Inject
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import models.parsingOpenCSV
import java.util.Calendar
import java.text.SimpleDateFormat

import scala.collection.immutable
import scala.collection.parallel.immutable.ParSeq


case class ApplicationDetails(firstname: String, lastname: String, position: String)
case class CountryData(name: String)

case class UserTask(label: String, who: String)

case class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport{

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def query = Action { implicit request =>
    Ok(views.html.query(List((("", "", "", "", ""), List(("", "", "", "", "", "", "")))), countryForm, 0L )) // pass airports
  }

  def report = Action { implicit request => {
    val (top10: Seq[(String, Int)], bottom10: Seq[(String, Int)]) = parsingOpenCSV.topCountry
    val surfacesTop10: Seq[(String, Int)] = parsingOpenCSV.typeRunways
    val topLeIdent: Seq[(String, Int)] = parsingOpenCSV.topLeIdent
    Ok(views.html.report(top10, bottom10, surfacesTop10, topLeIdent))
  }
  }

  val details = Form(
    mapping(
      "firstname" -> nonEmptyText,
      "lastname" -> nonEmptyText,
      "position" -> nonEmptyText
    )(ApplicationDetails.apply)
    (ApplicationDetails.unapply)
  )

  val countryForm = Form(
    mapping(
      "Country name" -> nonEmptyText,
    )(CountryData.apply)(CountryData.unapply)
  )

//  def apply = Action { implicit request =>
//    Ok(views.html.apply())
//  }

//  def submit = Action(parse.multipartFormData)  { implicit request =>
//    details.bindFromRequest.fold(
//      errors => BadRequest(views.html.apply()),
//      details => {
//        request.body.file("coverletter").map { coverletter =>
//          val target = new java.io.File(s"./uploads/${coverletter.filename}")
//          coverletter.ref.moveTo(target, true)
//        }
//        request.body.file("cv").map { cv =>
//          val target = new java.io.File(s"./uploads/${cv.filename}")
//          cv.ref.moveTo(target, true)
//        }
//        Redirect(routes.HomeController.index())
//      }
//    )
//  }

  val taskForm = Form(
    mapping (
      "label" -> nonEmptyText,
      "who" -> nonEmptyText
    )(UserTask.apply)(UserTask.unapply)
  )

//  def tasks = Action { implicit request =>
//    Ok(views.html.todo(models.Task.all(), taskForm))
//  }

  def newTask = Action { implicit request =>
    countryForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.html.index())
      },
      countryData => {
        val startTime: Long = System.currentTimeMillis()
        val listResult = parsingOpenCSV.findData(countryData.name)
          .seq.map(rightTuple => (rightTuple._1,rightTuple._2.seq.map{x=> x}))
        Ok(views.html.query(listResult, countryForm, startTime))
      }
    )
  }



}
