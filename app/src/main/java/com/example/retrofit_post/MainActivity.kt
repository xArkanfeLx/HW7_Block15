package com.example.retrofit_post

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retrofit_post.utils.RandomDogInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var toolbarTB: Toolbar
    private lateinit var imgIV: ImageView
    private lateinit var loadBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
        loadBTN.setOnClickListener{
            getRandomDog()
        }
    }

    private fun getRandomDog() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RandomDogInstance.api.getRandomDog()
            } catch (e: IOException){
                Toast.makeText(applicationContext,"Ошибка...", Toast.LENGTH_SHORT).show()
                return@launch
            } catch (e: HttpException){
                Toast.makeText(applicationContext,"Ошибка сайта", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if(response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()
                    val imageUrl = data!!.url
                    val type = imageUrl.substring(20).split(".")[1].toLowerCase(Locale.ROOT)
                    if(type=="jpg" || type=="png") {
                        Picasso.get().load(imageUrl).into(imgIV)
                    } else getRandomDog()
                }
            }
        }
    }

    private fun init(){
        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        imgIV = findViewById(R.id.imgIV)
        loadBTN = findViewById(R.id.loadBTN)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}