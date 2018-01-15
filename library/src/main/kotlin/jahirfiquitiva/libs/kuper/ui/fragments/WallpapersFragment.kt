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
package jahirfiquitiva.libs.kuper.ui.fragments

import android.view.View
import ca.allanwang.kau.utils.dpToPx
import ca.allanwang.kau.utils.setPaddingBottom
import jahirfiquitiva.libs.frames.data.models.Wallpaper
import jahirfiquitiva.libs.frames.ui.fragments.base.BaseWallpapersFragment

class WallpapersFragment : BaseWallpapersFragment() {
    
    override fun initUI(content: View) {
        super.initUI(content)
        recyclerView?.setPaddingBottom(64.dpToPx)
    }
    
    override fun doOnWallpapersChange(data: ArrayList<Wallpaper>, fromCollectionActivity: Boolean) {
        super.doOnWallpapersChange(data, fromCollectionActivity)
        wallsAdapter?.setItems(ArrayList(data.subList(0, Math.min(data.size, 5000))))
    }
    
    override fun autoStartLoad(): Boolean = true
    override fun fromCollectionActivity(): Boolean = false
    override fun fromFavorites(): Boolean = false
    override fun showFavoritesIcon(): Boolean = false
}