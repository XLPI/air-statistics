package models

//import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader

import au.com.bytecode.opencsv.CSVReader

import scala.collection.JavaConverters._
import scala.collection.immutable
import scala.collection.parallel.immutable.ParSeq


case object parsingOpenCSV extends App {

  def topLeIdent = {
    val runway_id = 0
    val runway_airport_ref = 1
    val runway_airport_ident = 2
    val runway_length_ft = 3
    val runway_width_ft = 4
    val runway_surface = 5
    val runway_le_ident = 5
    val runways = getClass.getResource("/runways.csv").getPath
    val readerRunways = new CSVReader(new FileReader(runways)).readAll.asScala.toList.par

    val takeUnique = readerRunways.map{ r =>
      r(runway_le_ident)
    }.distinct


    val take = takeUnique.map{runway =>
      runway -> readerRunways.count(r => r(runway_le_ident) == runway)
    }.toVector.sortBy(runway => runway._2)

    val top10L = take.takeRight(10).reverse

    top10L
  }

  def typeRunways = {
    val runway_id = 0
    val runway_airport_ref = 1
    val runway_airport_ident = 2
    val runway_length_ft = 3
    val runway_width_ft = 4
    val runway_surface = 5
    val runway_le_ident = 5
    val runways = getClass.getResource("/runways.csv").getPath
    val readerRunways = new CSVReader(new FileReader(runways)).readAll.asScala.toList.par

    val takeUnique = readerRunways.map{ r =>
      r(runway_surface)
    }.distinct


    val take = takeUnique.map{runway =>
      runway -> readerRunways.count(r => r(runway_surface) == runway)
    }.toVector.sortBy(runway => runway._2)

    val top10R = take.takeRight(10).reverse

    top10R
  }

def topCountry: (Vector[(String, Int)], Vector[(String, Int)]) = {
  val countries = getClass.getResource("/countries.csv").getPath
  //  "id","code","name","continent","wikipedia_link","keywords"
  //  302672,"AD","Andorra","EU","http://en.wikipedia.org/wiki/Andorra",
  val country_code = 1
  val country_name = 2

  val airports = getClass.getResource("/airports.csv").getPath
  //  "id","ident","type","name","latitude_deg","longitude_deg","elevation_ft","continent","iso_country","iso_region","municipality","scheduled_service","gps_code","iata_code","local_code","home_link","wikipedia_link","keywords"
  //  6523,"00A","heliport","Total Rf Heliport",40.07080078125,-74.93360137939453,11,"NA","US","US-PA","Bensalem","no","00A",,"00A",,,
  val airport_id = 0
  val airport_ident = 1
  val airport_type = 2
  val airport_name = 3
  val airport_iso_country = 8

  val runways = getClass.getResource("/runways.csv").getPath
  //  "id","airport_ref","airport_ident","length_ft","width_ft","surface","lighted","closed","le_ident","le_latitude_deg","le_longitude_deg","le_elevation_ft","le_heading_degT","le_displaced_threshold_ft","he_ident","he_latitude_deg","he_longitude_deg","he_elevation_ft","he_heading_degT","he_displaced_threshold_ft",
  //  269408,6523,"00A",80,80,"ASPH-G",1,0,"H1",,,,,,,,,,,
  val runway_id = 0
  val runway_airport_ref = 1
  val runway_airport_ident = 2
  val runway_length_ft = 3
  val runway_width_ft = 4
  val runway_surface = 5
  val runway_le_ident = 5

  val readerCountries = new CSVReader(new FileReader(countries)).readAll.asScala.toList.par
  val readerAirports = new CSVReader(new FileReader(airports)).readAll.asScala.toList.par
  val readerRunways = new CSVReader(new FileReader(runways)).readAll.asScala.toList.par

  val take10 = readerCountries.filter(country => country(country_name) != "name").map{ country => {
      country(country_name) -> readerAirports.count{a => a(airport_iso_country) == country(country_code)}
    }
  }.toVector.sortBy(country => country._2)

    val top10 = take10.takeRight(10).reverse
    val bottom10 = take10.take(10).reverse

//   top10.foreach(country => println(s"TOP 10 Country: ${country._1}, Airports: ${country._2}"))
//  bottom10.foreach(country => println(s"BOTTOM 10 Country: ${country._1}, Airports: ${country._2}"))
  (top10,bottom10)
}



  def findData(input: String) = {
//  val inputCountry = input.toLowerCase.toUpperCase
  val inputCountry = input.toLowerCase.toUpperCase

    // partial input find
//    val inputCountryCode = readerCountries.filter(country => {
//      country.contains(country(country_code)) || country.contains(country(country_name))
//    }).map(country => country(country_code))
//    println(s"Debug - country is  - ${inputCountryCode}")

    val countries = getClass.getResource("/countries.csv").getPath
    //  "id","code","name","continent","wikipedia_link","keywords"
    //  302672,"AD","Andorra","EU","http://en.wikipedia.org/wiki/Andorra",
    val country_code = 1
    val country_name = 2

    val airports = getClass.getResource("/airports.csv").getPath
    //  "id","ident","type","name","latitude_deg","longitude_deg","elevation_ft","continent","iso_country","iso_region","municipality","scheduled_service","gps_code","iata_code","local_code","home_link","wikipedia_link","keywords"
    //  6523,"00A","heliport","Total Rf Heliport",40.07080078125,-74.93360137939453,11,"NA","US","US-PA","Bensalem","no","00A",,"00A",,,
    val airport_id = 0
    val airport_ident = 1
    val airport_type = 2
    val airport_name = 3
    val airport_iso_country = 8

    val runways = getClass.getResource("/runways.csv").getPath
    //  "id","airport_ref","airport_ident","length_ft","width_ft","surface","lighted","closed","le_ident","le_latitude_deg","le_longitude_deg","le_elevation_ft","le_heading_degT","le_displaced_threshold_ft","he_ident","he_latitude_deg","he_longitude_deg","he_elevation_ft","he_heading_degT","he_displaced_threshold_ft",
    //  269408,6523,"00A",80,80,"ASPH-G",1,0,"H1",,,,,,,,,,,
    val runway_id = 0
    val runway_airport_ref = 1
    val runway_airport_ident = 2
    val runway_length_ft = 3
    val runway_width_ft = 4
    val runway_surface = 5
    val runway_le_ident = 5

    val readerCountries = new CSVReader(new FileReader(countries)).readAll.asScala.toList.par
    val readerAirports = new CSVReader(new FileReader(airports)).readAll.asScala.toList.par
    val readerRunways = new CSVReader(new FileReader(runways)).readAll.asScala.toList.par


    ///////////////////////////////////
  val selectedCountry = readerCountries.filter(country => {
    country(country_code) == inputCountry || country(country_name) == inputCountry
  }).map(country => country(country_code)).headOption.getOrElse("")

  //  val airportsForChoosedCountry = readerAirports.foreach(ap =>
  //  println(s"ID: ${ap(airport_id)},IDENT: ${ap(airport_ident)},TYPE: ${ap(airport_type)},NAME: ${ap(airport_name)}" +
  //    s"COUNTRY: ${ap(airport_iso_country)}"))

  val airportsForSelectedCountry = readerAirports
    .filter(airport =>
      airport(airport_iso_country) == selectedCountry)
    .map(ap =>
      (ap(airport_id), ap(airport_ident), ap(airport_type), ap(airport_name), ap(airport_iso_country)))

  val runwaysList = readerRunways.map(runway =>
    (runway(runway_id), runway(runway_airport_ref), runway(runway_airport_ident), runway(runway_length_ft), runway(runway_width_ft),
      runway(runway_surface), runway(runway_le_ident)))

  val airportsWithRunways = airportsForSelectedCountry
    .map(airport => {
    airport -> runwaysList
      .filter(runway =>
      runway._2 == airport._1)
  })

    airportsWithRunways
  }



  // for debug
  //topCountry
  typeRunways
}