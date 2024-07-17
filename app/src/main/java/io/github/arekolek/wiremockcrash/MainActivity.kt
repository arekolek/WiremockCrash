package io.github.arekolek.wiremockcrash

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Pattern.compile("\\{(?<variable>[^}]+)\\}|(?<wildcard>\\*\\*)")
        try {
            Pattern.compile("\\{(?<variable>[^}]+)}|(?<wildcard>\\*\\*)")
            error("Expecting above pattern to fail")
        } catch (_: PatternSyntaxException) {
        }

        // Bug reproduction:
        val server = WireMockServer(WireMockConfiguration.wireMockConfig())
        server.start()
        WireMock.configureFor(server.port())
    }
}
