package com.example.miniproyecto1



import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.miniproyecto1.viewmodel.CompartirViewModel

import org.junit.Before

import org.junit.Test

import org.mockito.Mockito.verify





import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.runner.RunWith

import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
@RunWith(AndroidJUnit4::class)
class CompartirViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: CompartirViewModel
    private lateinit var shadowActivity: ShadowActivity

    @Before
    fun setUp() {
        viewModel = CompartirViewModel()
        val activity = Robolectric.setupActivity(Activity::class.java)
        shadowActivity = Shadows.shadowOf(activity)
    }

    @Test
    fun compartirApp_createsCorrectIntent() {
        viewModel.compartirApp(shadowActivity.applicationContext)

        val startedIntent = shadowActivity.nextStartedActivity
        assertEquals(Intent.ACTION_SEND, startedIntent.action)
        assertEquals("App pico botella\nSolo los valientes lo juegan!!\nhttps://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es", startedIntent.getStringExtra(Intent.EXTRA_TEXT))
        assertEquals("text/plain", startedIntent.type)
    }
}