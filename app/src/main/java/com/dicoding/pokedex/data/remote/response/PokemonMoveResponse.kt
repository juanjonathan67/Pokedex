package com.dicoding.pokedex.data.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonMoveResponse(

	@field:SerializedName("generation")
	val generation: Generation? = null,

	@field:SerializedName("pp")
	val pp: Int? = null,

	@field:SerializedName("stat_changes")
	val statChanges: List<Any?>? = null,

	@field:SerializedName("accuracy")
	val accuracy: Int? = null,

	@field:SerializedName("contest_combos")
	val contestCombos: ContestCombos? = null,

	@field:SerializedName("priority")
	val priority: Int? = null,

	@field:SerializedName("super_contest_effect")
	val superContestEffect: SuperContestEffect? = null,

	@field:SerializedName("type")
	val type: Type? = null,

	@field:SerializedName("effect_changes")
	val effectChanges: List<Any?>? = null,

	@field:SerializedName("learned_by_pokemon")
	val learnedByPokemon: List<LearnedByPokemonItem?>? = null,

	@field:SerializedName("target")
	val target: Target? = null,

	@field:SerializedName("effect_entries")
	val effectEntries: List<EffectEntriesItem?>? = null,

	@field:SerializedName("contest_type")
	val contestType: ContestType? = null,

	@field:SerializedName("past_values")
	val pastValues: List<Any?>? = null,

	@field:SerializedName("names")
	val names: List<NamesItem?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("flavor_text_entries")
	val flavorTextEntries: List<FlavorTextEntriesItem?>? = null,

	@field:SerializedName("damage_class")
	val damageClass: DamageClass? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("effect_chance")
	val effectChance: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("machines")
	val machines: List<Any?>? = null,

	@field:SerializedName("power")
	val power: Int? = null,

	@field:SerializedName("contest_effect")
	val contestEffect: ContestEffect? = null
)


data class Normal(

	@field:SerializedName("use_after")
	val useAfter: Any? = null,

	@field:SerializedName("use_before")
	val useBefore: List<UseBeforeItem?>? = null
)

data class SuperContestEffect(

	@field:SerializedName("url")
	val url: String? = null
)

data class UseBeforeItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class EffectEntriesItem(

	@field:SerializedName("short_effect")
	val shortEffect: String? = null,

	@field:SerializedName("effect")
	val effect: String? = null,

	@field:SerializedName("language")
	val language: Language? = null
)

data class ContestEffect(

	@field:SerializedName("url")
	val url: String? = null
)

data class Meta(

	@field:SerializedName("healing")
	val healing: Int? = null,

	@field:SerializedName("min_hits")
	val minHits: Any? = null,

	@field:SerializedName("max_hits")
	val maxHits: Any? = null,

	@field:SerializedName("ailment_chance")
	val ailmentChance: Int? = null,

	@field:SerializedName("crit_rate")
	val critRate: Int? = null,

	@field:SerializedName("flinch_chance")
	val flinchChance: Int? = null,

	@field:SerializedName("min_turns")
	val minTurns: Any? = null,

	@field:SerializedName("ailment")
	val ailment: Ailment? = null,

	@field:SerializedName("category")
	val category: Category? = null,

	@field:SerializedName("max_turns")
	val maxTurns: Any? = null,

	@field:SerializedName("drain")
	val drain: Int? = null,

	@field:SerializedName("stat_chance")
	val statChance: Int? = null
)

data class Category(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class ContestType(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class LearnedByPokemonItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class ContestCombos(

	@field:SerializedName("super")
	val jsonMemberSuper: JsonMemberSuper? = null,

	@field:SerializedName("normal")
	val normal: Normal? = null
)

data class DamageClass(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class Target(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class Ailment(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class JsonMemberSuper(

	@field:SerializedName("use_after")
	val useAfter: Any? = null,

	@field:SerializedName("use_before")
	val useBefore: Any? = null
)
