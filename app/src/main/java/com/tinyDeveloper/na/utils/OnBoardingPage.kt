package com.tinyDeveloper.na.utils

import androidx.annotation.DrawableRes
import com.tinyDeveloper.na.R

sealed class OnBoardingPage(
    @DrawableRes
    val image:Int,
    val title:String,
    val description:String
){
    object First: OnBoardingPage(
        image = R.drawable.ordering_not,
        title = "اعلانات عمومی و خصوصی",
        description = "اطلاعیه ها بصورت عمومی برای همه کارکنان فرستاده میشود و اطلاعات خصوصی برای کارکنان یک بخش خاص فرستاده میشوند."
    )

    object Second: OnBoardingPage(
        image = R.drawable.ordering_jobs,
        title = "کارخاسته ها",
        description = "وظایف هر بخش(بخش ها) برای کارکنان ان فرستاده میشود و کارکنان در جواب میتوانند توضیحات یا انواع فایل ها را ارسال کنند و امتیاز بگیرند."
    )

    object Third: OnBoardingPage(
        image = R.drawable.ordering_users,
        title = "همکاران",
        description = "در ای بخش همه کارکنان قابل مشاهده بوده و میتوان با انها گفتگویی اغاز کرد و کاربران هر بخش بر اساس امتیاز رتبه بندی میشوند."
    )

    object For: OnBoardingPage(
        image = R.drawable.ordering_parts,
        title = "بخش ها",
        description = "هر سازمان میتواند بخش ها یا شعبه هایی داشته باشد که در بخش تنظیمات قابل ویرایش است همچنین تقسیم بندی کارکنان باعث رقابت عادلانه میشود."
    )

    object Five: OnBoardingPage(
        image = R.drawable.ordering_chat,
        title = "گفتگوی برخط",
        description = "کاربران در یک گروه مربوط به کل مجموعه عضو هستند و میتوانند بحث و گفتگو کنند، امکان گفتگوی شخصی بین کاربران جهت هماهنگی بیشتر فراهم است."
    )
}
