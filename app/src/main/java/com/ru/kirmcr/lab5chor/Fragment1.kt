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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

private const val KEY_LOGIN = "TAG_LOGIN"
private const val KEY_PASSWORD = "TAG_PASSWORD"

class Fragment1 : Fragment() {

    private lateinit var info: TextView
    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var enter: Button
    private lateinit var reg: Button

    private val userListViewModel: UserListViewModel by lazy {
        ViewModelProvider(this)[UserListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val login: String = arguments?.getString(KEY_LOGIN) as String
        val password: String = arguments?.getString(KEY_PASSWORD) as String
        Log.d("FRAGMENT1", "login:${login} password:${password}")
        userListViewModel.addUser(login, password)
    }

    //создаем колбэк для реализиации переходов между фрагментами
    interface Callbacks {
        fun onButtonSelected(type: Int)
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
        val view = inflater.inflate(R.layout.fragment_1, container, false)
        info = view.findViewById(R.id.info)
        login = view.findViewById(R.id.login)
        password = view.findViewById(R.id.password)
        enter = view.findViewById(R.id.enter)
        reg = view.findViewById(R.id.reg)
        return view
    }

    override fun onStart() {
        super.onStart()

        //объявляем анонимный класс TextWatcher который наюлюдает за изменением текста,
        //для того, чтобы избежать его сраабатывания при пересоздании фрагмента(также как и OnCheckChangedListener) -
        // - необходимо объявить его в функции onStart()
        val titleWatcher = object : TextWatcher {
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
        login.addTextChangedListener(titleWatcher)

        val titleWatcherPassword = object : TextWatcher {
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
        password.addTextChangedListener(titleWatcherPassword)

        enter.setOnClickListener {
            enter.setText(R.string.wait)
            enter.isEnabled = false
            reg.visibility = View.GONE
            android.os.Handler().postDelayed({
                enter.isEnabled = true
                reg.visibility = View.VISIBLE
                enter.setText(R.string.enter)
                if (userListViewModel.userWithComprasion()) {

                    callbacks?.onButtonSelected(R.string.enter_key)
                }
                info.setText(userListViewModel.textError)
            }, 3001)

            //
//            if (userListViewModel.userWithComprasion()) {
//                enter.setText(R.string.wait)
//                enter.isEnabled = false
//                reg.visibility = View.GONE
//                android.os.Handler().postDelayed({
//                    enter.isEnabled = true
//                    reg.visibility = View.VISIBLE
//                    enter.setText(R.string.enter)
//                    info.setText(userListViewModel.textError)
//                    callbacks?.onButtonSelected(R.string.enter_key)
//                }, 3001)
//
//            }

        }
        reg.setOnClickListener {
            callbacks?.onButtonSelected(R.string.reg_key)
        }

    }

    override fun onPause() {
        super.onPause()
        login.text = null
        password.text = null

    }

    companion object {
        fun newInstance(login: String, password: String): Fragment1 {
            val args = Bundle().apply {
                putCharSequence(KEY_LOGIN, login)
                putCharSequence(KEY_PASSWORD, password)
            }
            return Fragment1().apply {
                arguments = args
            }
        }
    }


}