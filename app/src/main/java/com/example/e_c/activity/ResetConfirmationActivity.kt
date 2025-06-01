
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.e_c.databinding.ActivityResetConfirmationBinding

 class ResetConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}