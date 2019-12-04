package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.deserializer.errors.DeserializationException
import net.jemzart.jsonkraken.JsonString

import org.junit.Test

class StringDeserialization {
	@Test(expected = DeserializationException::class)
	fun `premature end`() {
		JsonKraken.deserialize<JsonString>("\"")
	}
}