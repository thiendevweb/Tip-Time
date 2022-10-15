package com.example.createxmllayout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.createxmllayout.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding// khai bảo thuộc tính binding ở top-level để có thể sử dụng ở nhiều method in class

    //    Tên của binding class được tạo ra bằng cách chuyển đổi tên của tệp XML thành dạng chữ Pascal và thêm từ "Binding" vào cuối.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding =
            ActivityMainBinding.inflate(layoutInflater)// khởi tạo binding object bằng các gọi phương thức iflate(tham số: thuộc tính layoutInfater (thay cho getlayoutInfater method))
        setContentView(binding.root)// thay vì truyền ID của layout(R.layout.activity_main), chúng ta sẽ truyền tham số root , chỉ định tận gốc của the hierarchy of views(hệ thống phân cấp chế độ xem: ở đây là layout chứa các Views child)
        //tham chiếu đến các View in layout đơn giản hơn rất nhiều , binding.view_id = findViewById(R.id.view_id)
        binding.calculateButton.setOnClickListener {
            calculateTip()
        }

        // thiết lập một trình lắng nghe và xử lý khi 1 phím cứng được nhấn,
        // tham số truyền vào sẽ là onKey() method của interface onKeylistener
        // onKey() là 1 funtion có tham số là, phương thức đó được triến khai bằng 1 biểu thức lambda có cùng signature
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    //method for hide of KeyBoard
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        // Hide the keyboard
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //Lấy một mã thông báo duy nhất xác định cửa sổ mà chế độ xem này được đính kèm.(view.windowToken)
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun calculateTip() {
        // text atrribute of EditText is Editable nên phải conver toString()
        // method giúp chuyển String thành Double, nếu kh đc nó sẽ trả về null
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        if (cost != null) {
            // get thuộc tính checkedRadioButton : trả về id của RadioButton đã checked
            val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
                R.id.option_twenty_percent -> 0.2
                R.id.option_eighteen_percent -> 0.18
                else -> 0.15
            }
            //For a Switch element, you can check the isChecked attribute to see if the switch is "on".
            var tip = tipPercentage * cost
            val roundUp = binding.roundUpSwitch.isChecked
            if (roundUp) tip = ceil(tip)
            val formatTip = NumberFormat.getCurrencyInstance().format(tip)
            binding.tipResult.text = resources.getString(R.string.tip_amount, formatTip)
        } else {
            binding.tipResult.text = ""
            return
        }
    }
}//You may recall the idea of parent views and child views; the root connects to all of them.
// thay dùng findViewByIdI() để tham chiếu từng View
// bạn có thể sử dụng binding object để tham chiếu tới tất cả các View trong 1 hierarchy View (ví dụ, 1 layout): phan cấp theo kiểu thùng chứa, parent View chứa child View
//Use the checkedRadioButtonId attribute of a RadioGroup to find which RadioButton is selected.
//Sử dụng NumberFormat.getCurrencyInstance()để lấy một bộ định dạng để sử dụng cho việc định dạng số dưới dạng đơn vị tiền tệ.
// Bạn có thể sử dụng các tham số chuỗi như %sđể tạo chuỗi động mà vẫn có thể dễ dàng dịch sang các ngôn ngữ khác.
//Testing is important!
// Use Analyze > Inspect Code để biết các đề xuất nhằm cải thiện mã của bạn.
// nhớ enable binding trong build.gradle (:app)
/*
    buildFeatures {
        viewBinding = true
    }

   View Binding là một tính năng cho phép bạn viết mã tương tác với các chế độ xem dễ dàng hơn.
     Khi tính năng view binding được kích hoạt(enable) trong một mô-đun, nó sẽ tạo ra một binding class cho mỗi tệp layout XML có trong mô-đun đó.
    Một thể hiện của binding class chứa các tham chiếu trực tiếp đến tất cả cac Views có ID trong layout tương ứng.
    Thư viện Material Design Components (MDC) cần được đưa vào như một phần phụ(dependency) thuộc trong dự án của bạn
    dependencies {
    ...
    implementation 'com.google.android.material:material:<version>'
    }
Material Component là các tiện ích giao diện người dùng phổ biến giúp triển khai kiểu MATERIAL trong ứng dụng của bạn dễ dàng hơn.
Style là một tập hợp các giá trị thuộc tính viewcho một loại widget.
* */
