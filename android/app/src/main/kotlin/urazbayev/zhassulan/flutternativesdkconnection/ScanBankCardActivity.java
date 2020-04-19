package urazbayev.zhassulan.flutternativesdkconnection;

import android.app.Activity;
import android.content.Intent;

import ru.yandex.money.android.sdk.Checkout;


public class ScanBankCardActivity extends Activity {

    private void onScanningDone(final String cardNumber, final int expirationMonth, final int expirationYear) {

        final Intent result = Checkout.createScanBankCardIntent(cardNumber, expirationMonth, expirationYear);

        setResult(Activity.RESULT_OK, result);

        finish();

    }
}
