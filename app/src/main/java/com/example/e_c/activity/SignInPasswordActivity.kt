
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.e_c.databinding.ActivitySignInEmailBinding

class SignInPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}