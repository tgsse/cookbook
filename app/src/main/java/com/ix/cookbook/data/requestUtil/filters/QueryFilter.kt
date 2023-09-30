package com.ix.cookbook.data.requestUtil.filters

data class QueryFilter(
    override val value: String,
) : Filter {
    override val label = "Search"
}
