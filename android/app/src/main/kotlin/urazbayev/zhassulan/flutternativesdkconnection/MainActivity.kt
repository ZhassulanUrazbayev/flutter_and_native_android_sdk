package urazbayev.zhassulan.flutternativesdkconnection

import android.app.Activity
import android.content.Intent
import android.content.RestrictionsManager.RESULT_ERROR
import android.os.Bundle
import android.util.Log
import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import ru.yandex.money.android.sdk.*
import java.math.BigDecimal
import java.util.*

class MainActivity: FlutterActivity() {

    private val REQUEST_CODE_TOKENIZE = 33

    companion object {
        const val CHANNEL = "urazbayev.zhassulan.flutternativesdkconnection"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)

        MethodChannel( flutterView, CHANNEL).setMethodCallHandler { call, result ->
            // manage method calls here
            if (call.method == "showNativeView") {
                timeToStartCheckoutTest()
                result.success(true)
            } else {
                result.notImplemented()
            }
        }
    }

    private fun timeToStartCheckoutTest() {
        val paymentParameters = PaymentParameters(
                Amount(BigDecimal.ZERO, Currency.getInstance("RUB")),
                "Название товара",
                "Описание товара",
                "test_Njk3NjI2ivr2zUxL7rd_nsxzAcLKgHtm9C5vjDpSGQY",
                "697626",
                SavePaymentMethod.OFF,
                getPaymentMethodTypes())

        val testParameters = TestParameters(true, true,
                MockConfiguration(false, true, 5, Amount(BigDecimal.TEN, Currency.getInstance("RUB"))));
        val intent = Checkout.createTokenizeIntent(this, paymentParameters, testParameters);
        startActivityForResult(intent, REQUEST_CODE_TOKENIZE);
    }

    fun timeToStart3DS() {
        val intent = Checkout.create3dsIntent(
                this,
                "https://3dsurl.com/"
        )
        startActivityForResult(intent, 1)
    }

    private fun getPaymentMethodTypes(): Set<PaymentMethodType> {
        val paymentMethodTypes: MutableSet<PaymentMethodType> = HashSet()
//        if (settings.isYandexMoneyAllowed()) {
//            paymentMethodTypes.add(PaymentMethodType.YANDEX_MONEY)
//        }
        paymentMethodTypes.add(PaymentMethodType.BANK_CARD)
        paymentMethodTypes.add(PaymentMethodType.SBERBANK)
        paymentMethodTypes.add(PaymentMethodType.GOOGLE_PAY)
        return paymentMethodTypes
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        val channel = MethodChannel( flutterView, CHANNEL)

        if (requestCode == REQUEST_CODE_TOKENIZE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    channel.invokeMethod("message", "RESULT_OK Inside Token Get")
                    Log.d("Success_Result", "RESULT_OKInsideToken")
                }
                Activity.RESULT_CANCELED -> {
                    channel.invokeMethod("message", "RESULT_CANCELED Inside Token Get")
                    Log.d("Success_Result", "RESULT_CANCELEDInsideToken")
                }
            }
        }

        if (requestCode == 1) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    channel.invokeMethod("message", "RESULT_OK")
                    Log.d("Success_Result", "RESULT_OK")
                }
                Activity.RESULT_CANCELED -> {
                    channel.invokeMethod("message", "RESULT_CANCELED")
                    Log.d("Success_Result", "RESULT_CANCELED")

                }
                RESULT_ERROR -> {
                    channel.invokeMethod("message", "RESULT_ERROR")
                    Log.d("Success_Result", "RESULT_ERROR")
                }
            }
        }
    }


}
