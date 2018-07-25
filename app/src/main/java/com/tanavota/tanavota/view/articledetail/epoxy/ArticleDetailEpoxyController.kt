package com.tanavota.tanavota.view.articledetail.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.tanavota.tanavota.*
import com.tanavota.tanavota.model.domain.articledetail.ArticleDetailAdditionalInformationBorder_
import com.tanavota.tanavota.viewmodel.articledetail.ArticleDetailModelable
import com.tanavota.tanavota.viewmodel.articledetail.ArticleDetailViewModel
import java.util.*

class ArticleDetailEpoxyController : TypedEpoxyController<ArticleDetailModelable>() {
    override fun buildModels(data: ArticleDetailModelable) {
        val viewModel = data as? ArticleDetailViewModel ?: return
        val id = viewModel.articleId
        val borderId = fun(): () -> String {
            val uuid = UUID.randomUUID().toString()
            var count = 0
            return { "border_${uuid}_${count++}" }
        }()

        ArticleDetailTitleBindingModel_()
                .id("${id}_title")
                .article(viewModel.article)
                .addTo(this)

        ArticleDetailBasicInformationBindingModel_()
                .id("${id}_basic_information")
                .article(viewModel.article)
                .addTo(this)

        ArticleDetailTransferButtonBindingModel_()
                .id("${id}_transfer_button_1")
                .viewModel(viewModel)
                .addTo(this)

        ArticleDetailImagesBindingModel_()
                .id("${id}_images")
                .viewModel(viewModel)
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_name")
                .captionId(R.string.name)
                .value(viewModel.article.name)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_traffic")
                .captionId(R.string.traffic)
                .value(viewModel.article.traffic)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_price")
                .captionId(R.string.price)
                .value(viewModel.article.price)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_yield")
                .captionId(R.string.yield)
                .value(viewModel.article.yield)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_location")
                .captionId(R.string.location)
                .value(viewModel.article.location)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_administrative_expense")
                .captionId(R.string.administrative_expense)
                .value(viewModel.article.administrative_expense)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_repair_financial_reserve")
                .captionId(R.string.repair_financial_reserve)
                .value(viewModel.article.repair_financial_reserve)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_tenancy")
                .captionId(R.string.tenancy)
                .value(viewModel.article.tenancy)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_premium")
                .captionId(R.string.premium)
                .value(viewModel.article.premium)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_deposit")
                .captionId(R.string.deposit)
                .value(viewModel.article.deposit)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_maintenance_cost")
                .captionId(R.string.maintenance_cost)
                .value(viewModel.article.maintenance_cost)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_lump_sum")
                .captionId(R.string.lump_sum)
                .value(viewModel.article.lump_sum)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_annual_planned_income")
                .captionId(R.string.annual_planned_income)
                .value(viewModel.article.annual_planned_income)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_layout")
                .captionId(R.string.layout)
                .value(viewModel.article.layout)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_storey")
                .captionId(R.string.storey)
                .value(viewModel.article.storey)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_building_area")
                .captionId(R.string.building_area)
                .value(viewModel.article.building_area)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_land_area")
                .captionId(R.string.land_area)
                .value(viewModel.article.land_area)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_building_structure")
                .captionId(R.string.building_structure)
                .value(viewModel.article.building_structure)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_time_old")
                .captionId(R.string.time_old)
                .value(viewModel.article.time_old)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_land_right")
                .captionId(R.string.land_right)
                .value(viewModel.article.land_right)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_city_planning")
                .captionId(R.string.city_planning)
                .value(viewModel.article.city_planning)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_restricted_zone")
                .captionId(R.string.restricted_zone)
                .value(viewModel.article.restricted_zone)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_coverage_ratio")
                .captionId(R.string.coverage_ratio)
                .value(viewModel.article.coverage_ratio)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_floor_area_ratio")
                .captionId(R.string.floor_area_ratio)
                .value(viewModel.article.floor_area_ratio)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_parking_lot")
                .captionId(R.string.parking_lot)
                .value(viewModel.article.parking_lot)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_motorcycle_place")
                .captionId(R.string.motorcycle_place)
                .value(viewModel.article.motorcycle_place)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_bicycle_parking_lot")
                .captionId(R.string.bicycle_parking_lot)
                .value(viewModel.article.bicycle_parking_lot)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_private_road_burden_area")
                .captionId(R.string.private_road_burden_area)
                .value(viewModel.article.private_road_burden_area)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_contact_with_road")
                .captionId(R.string.contact_with_road)
                .value(viewModel.article.contact_with_road)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_classification_of_land_and_category")
                .captionId(R.string.classification_of_land_and_category)
                .value(viewModel.article.classification_of_land_and_category)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_geographical_features")
                .captionId(R.string.geographical_features)
                .value(viewModel.article.geographical_features)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_the_total_number_of_houses")
                .captionId(R.string.the_total_number_of_houses)
                .value(viewModel.article.the_total_number_of_houses)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_country_law_report")
                .captionId(R.string.country_law_report)
                .value(viewModel.article.country_law_report)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_reform_and_renovation_important_notice")
                .captionId(R.string.reform_and_renovation_important_notice)
                .value(viewModel.article.reform_and_renovation_important_notice)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_special_report")
                .captionId(R.string.special_report)
                .value(viewModel.article.special_report)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_facilities")
                .captionId(R.string.facilities)
                .value(viewModel.article.facilities)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_remarks")
                .captionId(R.string.remarks)
                .value(viewModel.article.remarks)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_conditions")
                .captionId(R.string.conditions)
                .value(viewModel.article.conditions)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_present_condition")
                .captionId(R.string.present_condition)
                .value(viewModel.article.present_condition)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_delivery")
                .captionId(R.string.delivery)
                .value(viewModel.article.delivery)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_business_form")
                .captionId(R.string.business_form)
                .value(viewModel.article.business_form)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_occupied_area")
                .captionId(R.string.occupied_area)
                .value(viewModel.article.occupied_area)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_balcony")
                .captionId(R.string.balcony)
                .value(viewModel.article.balcony)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_pet")
                .captionId(R.string.pet)
                .value(viewModel.article.pet)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_site_area")
                .captionId(R.string.site_area)
                .value(viewModel.article.site_area)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_management_form")
                .captionId(R.string.management_form)
                .value(viewModel.article.management_form)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_information_pub_date")
                .captionId(R.string.information_pub_date)
                .value(viewModel.article.information_pub_date)
                .addTo(this)

        ArticleDetailAdditionalInformationBorder_()
                .id(borderId())
                .addTo(this)

        ArticleDetailAdditionalInformationRowBindingModel_()
                .id("${id}_next_time_update_due_date")
                .captionId(R.string.next_time_update_due_date)
                .value(viewModel.article.next_time_update_due_date)
                .addTo(this)

        ArticleDetailTransferButtonBindingModel_()
                .id("${id}_transfer_button_2")
                .viewModel(viewModel)
                .addTo(this)
    }
}