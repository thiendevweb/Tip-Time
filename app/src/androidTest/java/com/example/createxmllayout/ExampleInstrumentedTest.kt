package com.example.createxmllayout

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString
import androidx.test.espresso.assertion.ViewAssertions.matches

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class CalculatorTests {
    //ActivityScenarioRule đến từ thư viện AndroidX Test. Nó yêu cầu thiết bị khởi chạy một activity do nhà phát triển chỉ định.
    // Rule: qui định: chú thích chỉ định rằng qui tắc tiếp theo(trong th này là launch activity) sẽ thực thi trước mọi test case trong class
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_persent_tip() {
//        Hàm withId()trả về a ViewMatcherlà thành phần giao diện người dùng với ID được chuyển cho nó.
//        onView() returns a ViewInteraction là một đối tượng mà chúng ta có thể tương tác với nó như thể chúng ta đang điều khiển thiết bị.
//        Để nhập một số văn bản, bạn gọi perform() vào ViewInteraction,Then, perform() takes a ViewAction object.(trong th này viewAction là typeText)
//        Có một số phương thức trả về a ViewAction nhưng bây giờ chúng ta sẽ sử dụng typeText() method
//      typeText:  Trả về một hành động(action) chọn chế độ xem (bằng cách nhấp vào nó) và nhập chuỗi được cung cấp vào chế độ xem
//        perform method :Thực hiện (các) hành động(action) đã cho trên View được chọn bởi trình đối sánh View hiện tại.
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.calculate_button)).perform(click())
        //check Kiểm tra ViewAssertion đã cho trên view được chọn bởi trình so khớp view hiện tại
        //matches Trả về một ViewAssertion chung xác nhận rằng một view tồn tại trong phân cấp view và được đối sánh bởi trình so khớp chế độ xem đã cho.
        //withText Trả về một đối sánh phù hợp với TextViews dựa trên giá trị thuộc tính văn bản.
        //containsString: Tạo một trình đối sánh phù hợp nếu Chuỗi được kiểm tra có chứa Chuỗi được chỉ định ở bất kỳ đâu.
        onView(withId(R.id.tip_result)).check(matches(withText(containsString("10.00"))))
    }

    @Test
    fun calculate_18_persent_tip() {
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.option_eighteen_percent)).perform(click())
        onView(withId(R.id.calculate_button)).perform(click())
        onView(withId(R.id.tip_result)).check(matches(withText(containsString("9.00"))))
    }

    @Test
    fun calculate_15_persent_tip() {
        onView(withId(R.id.cost_of_service_edit_text)).perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.option_fifteen_percent)).perform(click())
        onView(withId(R.id.calculate_button)).perform(click())
        onView(withId(R.id.tip_result)).check(matches(withText(containsString("8.00"))))
        onView(withId(R.id.round_up_switch)).perform(click())
        onView(withId(R.id.calculate_button)).perform(click())
        onView(withId(R.id.tip_result)).check(matches(withText(containsString("7.50"))))
    }
}
/*
Bước đầu tiên là tìm một thành phần giao diện người dùng để tương tác,
trong trường hợp này là TextInputEditTextsử dụng onView()hàm. Hàm onView()nhận một ViewMatchertham số đối tượng.
ViewMatcher về cơ bản là một thành phần giao diện người dùng phù hợp với một tiêu chí cụ thể,
trong trường hợp này là một thành phần có ID R.id.cost_of_service_edit_text.
* */