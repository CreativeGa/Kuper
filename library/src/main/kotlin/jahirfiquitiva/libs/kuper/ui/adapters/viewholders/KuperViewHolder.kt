/*
 * Copyright (c) 2018. Jahir Fiquitiva
 *
 * Licensed under the CreativeCommons Attribution-ShareAlike
 * 4.0 International License. You may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *    http://creativecommons.org/licenses/by-sa/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jahirfiquitiva.libs.kuper.ui.adapters.viewholders

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import ca.allanwang.kau.utils.gone
import ca.allanwang.kau.utils.isVisible
import ca.allanwang.kau.utils.visible
import ca.allanwang.kau.utils.visibleIf
import com.afollestad.sectionedrecyclerview.SectionedViewHolder
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import jahirfiquitiva.libs.frames.helpers.extensions.releaseFromGlide
import jahirfiquitiva.libs.frames.helpers.utils.GlideRequestCallback
import jahirfiquitiva.libs.kauextensions.extensions.applyColorFilter
import jahirfiquitiva.libs.kauextensions.extensions.bind
import jahirfiquitiva.libs.kauextensions.extensions.formatCorrectly
import jahirfiquitiva.libs.kauextensions.extensions.getDrawable
import jahirfiquitiva.libs.kauextensions.extensions.isInPortraitMode
import jahirfiquitiva.libs.kauextensions.extensions.primaryTextColor
import jahirfiquitiva.libs.kauextensions.extensions.secondaryTextColor
import jahirfiquitiva.libs.kuper.R
import jahirfiquitiva.libs.kuper.data.models.KuperKomponent
import jahirfiquitiva.libs.kuper.helpers.extensions.tilesColor
import java.io.File

class KuperViewHolder(itemView: View) : SectionedViewHolder(itemView) {
    private val wall: ImageView? by itemView.bind(R.id.wall)
    private val preview: ImageView? by itemView.bind(R.id.preview)
    private val details: LinearLayout? by itemView.bind(R.id.komponent_details)
    private val name: TextView? by itemView.bind(R.id.komponent_name)
    private val app: TextView? by itemView.bind(R.id.komponent_app)
    private val icon: ImageView? by itemView.bind(R.id.launch_app)
    private val progress: ProgressBar? by itemView.bind(R.id.loading)
    
    fun bind(
            komponent: KuperKomponent,
            manager: RequestManager,
            wallpaper: Drawable?,
            listener: (KuperKomponent) -> Unit = {}
            ) {
        with(itemView) {
            wall?.setImageDrawable(wallpaper)
            details?.setBackgroundColor(context.tilesColor)
            name?.setTextColor(context.primaryTextColor)
            name?.text = komponent.name.formatCorrectly().replace("_", " ")
            app?.setTextColor(context.secondaryTextColor)
            app?.text = komponent.type.toString()
            icon?.visibleIf(komponent.hasIntent)
            if (icon?.isVisible == true) {
                icon?.setImageDrawable("ic_open_app".getDrawable(context))
                icon?.setOnClickListener { listener(komponent) }
            }
            val rightPreview =
                    if (context.isInPortraitMode) komponent.previewPath else komponent.rightLandPath
            
            try {
                progress?.indeterminateDrawable?.applyColorFilter(Color.parseColor("#888"))
            } catch (e: Exception) {
            }
            
            preview?.let {
                manager.load(File(rightPreview))
                        .apply(RequestOptions().priority(Priority.HIGH))
                        .listener(object : GlideRequestCallback<Drawable>() {
                            override fun onLoadSucceed(resource: Drawable): Boolean {
                                progress?.gone()
                                return false
                            }
                            
                            override fun onLoadFailed(): Boolean {
                                progress?.visible()
                                return super.onLoadFailed()
                            }
                        })
                        .into(it)
                        .clearOnDetach()
            }
        }
    }
    
    fun unbind() {
        preview?.releaseFromGlide()
        preview?.setImageDrawable(null)
        progress?.visible()
    }
}