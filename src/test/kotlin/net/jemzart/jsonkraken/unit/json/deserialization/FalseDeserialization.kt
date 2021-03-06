import net.jemzart.jsonkraken.JsonFalse
import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.errors.deserialization.DeserializationException
import org.junit.Test

class FalseDeserialization {
	@Test
	fun `false`() {
		val json = JsonKraken.deserialize<JsonFalse>("false")
		assert(json == JsonFalse)
	}

	@Test(expected = DeserializationException::class)
	fun `misspelled false`() {
		JsonKraken.deserialize<JsonFalse>("fffff")
	}

	@Test(expected = DeserializationException::class)
	fun `premature end`() {
		JsonKraken.deserialize<JsonFalse>("fals")
	}
}