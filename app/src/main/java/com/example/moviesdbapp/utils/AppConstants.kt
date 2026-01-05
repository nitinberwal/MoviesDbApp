package com.example.moviesdbapp.utils

class AppConstants {

    companion object {
        const val ACCEPT_TYPE = "application/json"
        const val API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NzcyNTNhZTY1OWFkMWQ5MTQyZWI1Yjg3OTU1OTFlYiIsIm5iZiI6MTc2NzE2NDA3NC43OTQsInN1YiI6IjY5NTRjOGFhN2MxYzg3NDk2ZWMzODQ0YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1nG4ITg4LDhz5scMG0_eTSocuVz70GoTFAc9yLHrJcc"
        const val POPULAR_MOVIES_LIST_SCREEN = 0
        const val NOW_PLAYING_MOVIES_LIST_SCREEN = POPULAR_MOVIES_LIST_SCREEN + 1
        const val SAVED_MOVIES_LIST_SCREEN = NOW_PLAYING_MOVIES_LIST_SCREEN + 1
        const val SEARCH_SCREEN = SAVED_MOVIES_LIST_SCREEN + 1

        const val MOVIE_SHARING_DEEPLINK = "https://www.moviesdbapp.com/movie/"
    }
}