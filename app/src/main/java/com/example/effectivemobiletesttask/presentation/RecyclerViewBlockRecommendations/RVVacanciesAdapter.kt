

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.Vacancies

class RVVacanciesAdapter(
    private var items: List<Vacancies> = listOf(
        Vacancies(),
        Vacancies(),
        Vacancies()
    )
) :
    RecyclerView.Adapter<RVVacanciesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPeopleViewing: TextView = itemView.findViewById(R.id.tv_people_viewing_rv_vacancies)
        val ivFavorite: ImageView = itemView.findViewById(R.id.iv_favorite_rv_vacancies)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title_rv_vacancies)
        val tvCity: TextView = itemView.findViewById(R.id.tv_city_rv_vacancies)
        val ivCompany: ImageView = itemView.findViewById(R.id.iv_company_rv_vacancies)
        val tvCompany: TextView = itemView.findViewById(R.id.tv_company_rv_vacancies)
        val ivExperience: ImageView = itemView.findViewById(R.id.iv_experience_rv_vacancies)
        val tvExperience: TextView = itemView.findViewById(R.id.tv_experience_rv_vacancies)
        val tvDatePublication: TextView =
            itemView.findViewById(R.id.tv_date_publication_rv_vacancies)
        val button: Button = itemView.findViewById(R.id.button_rv_vacancies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_vacancies, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        item.lookingNumber?.let { //если lookingNumber есть, то
            holder.tvPeopleViewing.text = checkingNumberLooking(it)
        }

        item.isFavorite?.let {
            if (it) holder.ivFavorite.setImageResource(R.drawable.ic_heart_blue)
            else holder.ivFavorite.setImageResource(R.drawable.ic_heart_gray_no_bable)
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
                this.isEnabled=true
                this.setBackgroundResource(R.drawable.green_button_selection)
            }
        }
        holder.tvDatePublication.text = item.publishedDate?.let { checkingData(it) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<Vacancies>) {
        this.items = limitingElements(newItems)
        notifyDataSetChanged()
    }

    private fun limitingElements(items: List<Vacancies>): // ограничение данных
            List<Vacancies> {//передать только первые 3 элемента
        return items.take(3)
    }

    private fun checkingNumberLooking(number: Int): String { //выбор склонения для просмотров
        return if ((number % 10 == 2 || number % 10 == 3 || number % 10 == 4) &&
            (number % 100 != 12 && number % 100 != 13 && number % 100 != 14)) {
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
}