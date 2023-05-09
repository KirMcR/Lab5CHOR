package com.ru.kirmcr.lab5chor

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class Fragment2 : Fragment() {
    private lateinit var info: TextView
    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var repeatPassword: EditText
    private lateinit var reg: Button

    private val userListViewModel: UserListViewModel by lazy {
        ViewModelProvider(this)[UserListViewModel::class.java]
    }

    //создаем колбэк для реализиации переходов между фрагментами
    interface Callbacks {
        fun onButtonRegSelected(login: String, password: String)
    }

    private var callbacks: Callbacks? = null

    /* Функция жизненного цикла Fragment.onAttach(Context) вызывается, когда фрагмент прикреплется к activity.
   Здесь вы помещаете аргумент Context, переданный функции onAttach(...), в свойство callback.
   Так как CrimeListFragment размещается в activity, то объект Context, переданный onAttach(...),
    является экземпляром activity, в которой размещен фрагмент.*/
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    /*Аналогичным образом нужно установить переменной значение null в соответствующей функции жизненного цикла Fragment.onDetach().
    Здесь переменную устанавливают равной нулю, так как в дальнейшем вы не сможете получить доступ к activity или рассчитывать на то,
    что она будет продолжать существовать.*/
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_2, container, false)
        info = view.findViewById(R.id.info)
        login = view.findViewById(R.id.login)
        password = view.findViewById(R.id.password)
        repeatPassword = view.findViewById(R.id.repeat_password)
        reg = view.findViewById(R.id.reg)
        userListViewModel.textRegError=R.string.empty
        return view

    }

    override fun onStart() {
        super.onStart()
        //объявляем анонимный класс TextWatcher который наюлюдает за изменением текста,
        //для того, чтобы избежать его сраабатывания при пересоздании фрагмента(также как и OnCheckChangedListener) -
        // - необходимо объявить его в функции onStart()
        val loginWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                userListViewModel.testUser.login = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
            }
        }
        login.addTextChangedListener(loginWatcher)
        val passwordWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                userListViewModel.testUser.password = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
            }
        }
        password.addTextChangedListener(passwordWatcher)

        val repeatPasswordWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                userListViewModel.repeatPassword = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
            }
        }
        repeatPassword.addTextChangedListener(repeatPasswordWatcher)

        reg.setOnClickListener {
            reg.setText(R.string.check)
            reg.isEnabled=false
            android.os.Handler().postDelayed({
                reg.isEnabled = true
                reg.setText(R.string.registration)
                if (userListViewModel.userRegComprasion()) {
                    Toast.makeText(context, "Пользователь  создан", Toast.LENGTH_SHORT).show()
                    callbacks?.onButtonRegSelected(userListViewModel.testUser.login, userListViewModel.testUser.password)
                }
                info.setText(userListViewModel.textRegError)
            }, 3001)


        }
    }

    companion object {
        fun newInstance(): Fragment2 {
            return Fragment2()
        }
    }
}