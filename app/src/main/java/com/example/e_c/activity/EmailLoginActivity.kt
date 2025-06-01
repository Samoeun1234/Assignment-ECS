
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.e_c.R

class EmailLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_login)

        findViewById<Button>(R.id.continueBtn).setOnClickListener {
            startActivity(Intent(this, PasswordLoginActivity::class.java))
        }
    }
}
