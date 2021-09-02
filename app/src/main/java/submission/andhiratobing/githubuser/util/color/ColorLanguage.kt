package submission.andhiratobing.githubuser.util.color

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import submission.andhiratobing.githubuser.R

object ColorLanguage {

    fun setColorLangauge(imageView: ImageView, language: String?) {
        imageView.visibility = if (language == null) View.GONE else View.VISIBLE
        if (language.isNullOrEmpty()) return
        imageView.setImageDrawable(getColorLanguage(imageView, language))
    }

    private fun getColorLanguage(imageView: ImageView, language: String?): Drawable {
        if (language == null) return ColorDrawable(Color.TRANSPARENT)

        val provideColor: (Int) -> Drawable = { resId ->
            ColorDrawable(ContextCompat.getColor(imageView.context, resId))
        }

        return provideColor(
            when (language) {
                "Kotlin" -> R.color.language_color_kotlin
                "Dart" -> R.color.language_color_dart
                "Java" -> R.color.language_color_java
                "JavaScript" -> R.color.language_color_javascript
                "Python" -> R.color.language_color_python
                "HTML" -> R.color.language_color_html
                "CSS" -> R.color.language_color_css
                "PHP" -> R.color.language_color_php
                "Swift" -> R.color.language_color_swift
                "Objective-C" -> R.color.language_color_objective_c
                "Shell" -> R.color.language_color_shell
                "C" -> R.color.language_color_c
                "C++" -> R.color.language_color_cplus
                "C#" -> R.color.language_color_ccresh
                "Ruby" -> R.color.language_color_ruby
                "Jupiter Notebook" -> R.color.language_color_jupiter_notebook
                "Go" -> R.color.language_color_go
                "TypeScript" -> R.color.language_color_type_script
                "Vue" -> R.color.language_color_vue
                "Rascal" -> R.color.language_color_rascal
                "Starlark" -> R.color.language_color_starlark
                "R" -> R.color.language_color_r
                "OCaml" -> R.color.language_color_o_caml
                "Gosu" -> R.color.language_color_gosu
                "Dockerfile" -> R.color.language_color_docker_file
                "GraphQL" -> R.color.language_color_graph_ql
                "Groovy" -> R.color.language_color_groovy
                "Julia" -> R.color.language_color_julia
                "MATLAB" -> R.color.language_color_matlab
                "PowerShell" -> R.color.language_color_power_shell
                else -> R.color.language_color_other
            }
        )
    }
}

