package com.arthur.pos.retrofit_currency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.arthur.pos.retrofit_currency.api.Endpoint
import com.arthur.pos.retrofit_currency.utils.NetworkUtils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var edtReq: TextView
    private lateinit var edtReqAntiga: TextView
    private var start : Long = 0
    private var finish : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtReq = findViewById(R.id.edtReq)
        edtReqAntiga = findViewById(R.id.edtReqAntiga)

        getCurrencies()
        getCurrenciesAntigo()

    }

    fun getCurrencies(){
        start = System.currentTimeMillis()
        val retrofitClient = NetworkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencies().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println("Nao deu certo" + t.message)
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                println("Deu certo")
                var data = mutableListOf<String>()

                response.body()?.keySet()?.iterator()?.forEach { data.add(it)
                }
                edtReq.text = data.toString();
            }
        })
        finish = System.currentTimeMillis() - start
        println("o metodo via Retrofit foi executado em " + finish.toString() + "ms")
    }

    fun getCurrenciesAntigo(){
        Thread( Runnable{
            start = System.currentTimeMillis()
            val enderecoURL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json"
            val url = URL(enderecoURL)
            val urlConnection = url.openConnection()

            val inputStream = urlConnection.getInputStream()
            val entrada = BufferedReader(InputStreamReader(inputStream))
            val saida = StringBuilder()
            var caractere = entrada.readLine()

            while ( caractere != null) {
                saida.append( caractere )
                caractere = entrada.readLine()
            }
            println(saida.count())
            edtReqAntiga.text = saida.toString();
            finish = System.currentTimeMillis() - start
            println("o metodo da forma antiga foi executado em " + finish.toString() + "ms")
        }).start()
    }
}