package skydu.android.instaclone.utils

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

object TextViewClickUtil {
    fun setClickableText(view: TextView, text: CharSequence, clickable: List<Clickable>) {
        val ssb = SpannableStringBuilder(text)
        clickable.forEach {
            ssb.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        it.onClick()
                    }

                    override fun updateDrawState(ds: TextPaint) {

                    }
                },
                it.startIdx,
                it.endIdx,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )
        }
        view.text = ssb
        view.movementMethod = LinkMovementMethod.getInstance()
    }

    class Clickable(val startIdx: Int, val endIdx: Int, val onClick: (() -> Unit))
}