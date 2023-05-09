package com.ru.kirmcr.lab5chor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(), Fragment1.Callbacks, Fragment2.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Ищем фрагмент в фрагмент-менеджере
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        //Если в фрагмент-менеджере нет фрагмента - создаем транзакцию с новым фрагментом
        if (currentFragment == null) {
            val fragment = Fragment1.newInstance("1111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111")
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onButtonSelected(type: Int) {
        when (type) {
            R.string.enter_key -> {
                val fragment = Fragment3.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                    .addToBackStack(null).commit()
            }
            R.string.reg_key -> {
                val fragment = Fragment2.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                    .addToBackStack(null).commit()
            }
        }
    }

    override fun onButtonRegSelected(login: String, password: String) {

        val fragment = Fragment1.newInstance(login,password)
        Log.d("MAIN ACTIVITY", "login:${login} password:${password}")
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()


    }

}