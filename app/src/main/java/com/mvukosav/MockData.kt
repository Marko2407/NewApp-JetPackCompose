package com.mvukosav

import com.mvukosav.newsapp.R
import com.mvukosav.newsapp.models.NewsData
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.Locale

object MockData {
    val topNewsList = listOf<NewsData>(
        NewsData(
            newsId = 1,
            image = R.drawable.breaking_news,
            author = "Marko",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-03-11T03:01:00Z"
        ),
        NewsData(
            newsId = 2,
            image = R.drawable.news_,
            author = "Ivan",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda jer je najbolji",
            publishedAt = "2023-02-01T03:01:00Z"
        ),
        NewsData(
            newsId = 3,
            image = R.drawable.news_picture,
            author = "Matej",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda jer je najbolji",
            publishedAt = "2023-03-01T03:01:00Z"
        ),
        NewsData(
            newsId = 4,
            image = R.drawable.news_picture_2,
            author = "Mateo",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda jer je najjaci na svijetu",
            publishedAt = "2023-02-14T03:01:00Z"
        ),
        NewsData(
            newsId = 5,
            image = R.drawable.breaking_news,
            author = "Patrik",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-01-01T03:01:00Z"
        ),
        NewsData(
            newsId = 6,
            image = R.drawable.news_,
            author = "Antonio",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-01-01T03:01:00Z"
        ),
        NewsData(
            newsId = 7,
            image = R.drawable.news_picture,
            author = "Dea",
            title = "Dea nije legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-01-01T03:01:00Z"
        ),
        NewsData(
            newsId = 8,
            image = R.drawable.breaking_news,
            author = "Karla",
            title = "Karla nije legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-01-01T03:01:00Z"
        ),
        NewsData(
            newsId = 9,
            image = R.drawable.news_picture_2,
            author = "Mirna",
            title = "Mirna nije legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-01-01T03:01:00Z"
        ),
        NewsData(
            newsId = 10,
            image = R.drawable.breaking_news,
            author = "Davor",
            title = "Davor je legenda",
            description = "Kako je Marko postao legenda. Bio jednom jedan mladić po imenu Marko koji je živio u malom selu u planinskoj regiji. Marko je bio hrabar i pametan mladić, ali je također bio vrlo skroman i nije težio da bude u centru pažnje.\n" +
                "\n" +
                "Jednog dana, u selo je došao zli čarobnjak koji je odlučio da ukrade dragocjeno blago iz hrama u blizini sela. Čarobnjak je bio vrlo moćan, a ljudi u selu su se bojali za svoje živote i imovinu. Marko je shvatio da se nešto mora učiniti kako bi se zaustavio čarobnjak i spasio selo.\n" +
                "\n" +
                "Marko je odlučio da će se sam suprotstaviti čarobnjaku. Uzeo je svoj mač i krenuo prema hramu. Kada je stigao, vidio je čarobnjaka koji je upravo krenuo iz hrama sa zlatnim relikvijama. Marko je prvo pokušao uvjeriti čarobnjaka da odustane od svojih planova, no čarobnjak je bio prepotentan i ignorirao je Markove riječi.\n" +
                "\n" +
                "Zatim je započela epska bitka između Marka i čarobnjaka. Marko je pokazao svoju izvanrednu borilačku vještinu i hrabrost u borbi. Uz mnogo truda i snage, uspio je svladati čarobnjaka i vratiti dragocjeno blago u hram." +
                "\n" + "Vijest o Markovoj herojskoj borbi protiv čarobnjaka brzo se proširila po cijelom kraju, a ljudi su počeli dolaziti u selo kako bi se upoznali s hrabrim mladićem. Marko je odbio mnoga priznanja i nagrade za svoje djelo, ali je postao legenda među ljudima.\n" +
                "\n" +
                "Od tog dana, Marko je postao inspiracija za mnoge ljude",
            publishedAt = "2023-01-01T03:01:00Z"
        ),
    )

    fun getNews(newsId: Int?): NewsData {
        return topNewsList.first { it.newsId == newsId }
    }

    fun Date.getTimeAgo(): String {
        val calendar = Calendar.getInstance()
        calendar.time = this

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val currentCalendar = Calendar.getInstance()

        val currentYear = currentCalendar.get(Calendar.YEAR)
        val currentMonth = currentCalendar.get(Calendar.MONTH)
        val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentCalendar.get(Calendar.MINUTE)

        return if (year < currentYear) {
            val interval = currentYear - year
            if (interval == 1) "$interval year ago" else "$interval years ago"
        } else if (month < currentMonth) {
            val interval = currentMonth - month
            if (interval == 1) "$interval month ago" else "$interval months ago"
        } else if (day < currentDay) {
            val interval = currentDay - day
            if (interval == 1) "$interval day ago" else "$interval days ago"
        } else if (hour < currentHour) {
            val interval = currentHour - hour
            if (interval == 1) "$interval hour ago" else "$interval hours ago"
        } else if (minute < currentMinute) {
            val interval = currentMinute - minute
            if (interval == 1) "$interval minute ago" else "$interval minutes ago"
        } else {
            "a moment ago"
        }
    }

    fun stringToDate(publishedAt: String): Date {
        return Date.from(Instant.parse(publishedAt))
            ?: Date.from(Calendar.getInstance().toInstant())
    }
}
