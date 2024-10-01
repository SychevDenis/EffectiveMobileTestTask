import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.Vacancies

class RVVacanciesAdapter(
    private var listener: OnClickListenerAdapter
) :
    RecyclerView.Adapter<RVVacanciesAdapter.ViewHolder>() {
    private var items: List<Vacancies> = listOf()

    class ViewHolder(itemView: View, listener: OnClickListenerAdapter, items: List<Vacancies>) :
        RecyclerView.ViewHolder(itemView) {
        val lRvVacancies:LinearLayout = itemView.findViewById(R.id.layout_rv_vacancies)
        val tvPeopleViewing: TextView = itemView.findViewById(R.id.tv_people_viewing_rv_vacancies)
        val ibFavorite: ImageButton = itemView.findViewById(R.id.iv_favorite_rv_vacancies)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title_rv_vacancies)
        val tvCity: TextView = itemView.findViewById(R.id.tv_city_rv_vacancies)
        val ivCompany: ImageView = itemView.findViewById(R.id.iv_company_rv_vacancies)
        val tvCompany: TextView = itemView.findViewById(R.id.tv_company_rv_vacancies)
        val ivExperience: ImageView = itemView.findViewById(R.id.iv_experience_rv_vacancies)
        val tvExperience: TextView = itemView.findViewById(R.id.tv_experience_rv_vacancies)
        val tvDatePublication: TextView =
            itemView.findViewById(R.id.tv_date_publication_rv_vacancies)
        val button: Button = itemView.findViewById(R.id.button_rv_vacancies)

        init {
            ibFavorite.setOnClickListener {
                items[adapterPosition].id?.let {
                    listener.onClickAdapterButtonFavorites(it, adapterPosition)
                }
            }
            lRvVacancies.setOnClickListener{
                listener.onClickCard()
            }
            button.setOnClickListener {
                //ничего не делать
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_vacancies, parent, false)
        return ViewHolder(itemView, listener, items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        item.lookingNumber?.let { //если lookingNumber есть, то
            holder.tvPeopleViewing.text = checkingNumberLooking(it)
        } ?: run {
            holder.tvPeopleViewing.visibility = View.GONE
        }
        item.isFavorite?.let { //лайки
            if (it) holder.ibFavorite.setImageResource(R.drawable.ic_heart_blue)
            else holder.ibFavorite.setImageResource(R.drawable.ic_heart_gray_no_bubble)
        }
        holder.tvTitle.text = item.title
        holder.tvCity.text = item.address?.town
        item.company?.let {
            holder.tvCompany.text = it
            holder.ivCompany.setImageResource(R.drawable.ic_verification_company)
        }
        item.experience?.previewText?.let {
            holder.ivExperience.setImageResource(R.drawable.ic_work_experience)
            holder.tvExperience.text = it
            holder.button.apply {
                this.isEnabled = true
                this.setBackgroundResource(R.drawable.green_button_selection)
            }
        }
        holder.tvDatePublication.text = item.publishedDate?.let { checkingData(it) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<Vacancies>) { //метод обновления RV. Не рекомендуется
        // использовать, включил его лишь для того, что бы успеть в недельный срок
        this.items = newItems
        notifyDataSetChanged()
    }


    private fun checkingNumberLooking(number: Int): String { //выбор склонения для просмотров
        return if ((number % 10 == 2 || number % 10 == 3 || number % 10 == 4) &&
            (number % 100 != 12 && number % 100 != 13 && number % 100 != 14)
        ) {
            "Сейчас просматривает $number человека"
        } else {
            "Сейчас просматривает $number человек"
        }
    }

    private fun checkingData(data: String): String {//формирование даты и выбор склонения
        val months = listOf(
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"
        )
        val day = data.split("-")[2].toInt()
        val monthNumber = data.split("-")[1].toInt()
        return "Опубликовано $day ${months[monthNumber]}"
    }

    fun updateData(newDataList: List<Vacancies>) {
        //метод обновления RV. Сделано не верно, есть баги, исправлю их, если успею
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = items.size
            override fun getNewListSize(): Int = newDataList.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                items[oldItemPosition].id == newDataList[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                items[oldItemPosition].id == newDataList[newItemPosition].id
                        && items[oldItemPosition].isFavorite != newDataList[newItemPosition].isFavorite
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        items=newDataList.toList()
    }

    interface OnClickListenerAdapter {
        fun onClickAdapterButtonFavorites(id: String, position: Int)
        fun onClickCard()
    }
}
