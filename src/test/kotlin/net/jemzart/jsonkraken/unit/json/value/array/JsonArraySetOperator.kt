package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.errors.collections.CircularReferenceException
import net.jemzart.jsonkraken.errors.collections.InvalidIndexException
import net.jemzart.jsonkraken.errors.transformation.InvalidJsonTypeException
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import org.junit.Assert
import org.junit.Test

class JsonArraySetOperator {
	private val insertion = JsonArray()
	@Test
	fun `existing index by Int`() {
		val arr = JsonArray()
		arr.add(null)

		arr[0] = insertion

		assert(arr[0] == insertion)
	}

	@Test
	fun `existing index by String`() {
		val arr = JsonArray()
		arr.add(null)

		arr["0"] = insertion

		assert(arr[0] == insertion)
	}

	@Test
	fun `non existing index by Int`() {
		val arr = JsonArray()
		arr[0] = insertion
		assert(arr[0] == insertion)
	}

	@Test
	fun `non existing index by String`() {
		val arr = JsonArray()
		arr["0"] = insertion
		assert(arr[0] == insertion)
	}

	@Test
	fun `non existing index grows until index`() {
		val arr = JsonArray()
		arr[6] = insertion
		assert(arr.size == 7)
	}

	@Test
	fun `reverse notation`() {
		val arr = JsonArray()
		arr.add(null)
		arr.add(null)
		arr.add(null)

		arr[-2] = insertion

		assert(arr[1] == insertion)
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `fails on invalid type`() {
		val arr = JsonArray()
		arr["0"] = Exception()
	}

	@Test
	fun `circular reference not allowed`() {
		val arr = JsonArray()
		val obj = JsonObject("0" to arr)

		runCatching { arr[0] = obj }.onSuccess { assert(false) }.onFailure {
			it as CircularReferenceException
			assert(it.host == arr)
			assert(it.intruder == obj)
		}
	}

	@Test
	fun `self reference not allowed`() {
		val arr = JsonArray()

		runCatching { arr[0] = arr }.onSuccess { assert(false) }.onFailure {
			it as CircularReferenceException
			assert(it.host == arr)
			assert(it.intruder == arr)
		}
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> JsonArray()[0] = value }
	}


	@Test
	fun `by faulty String`() {
		val arr = JsonArray()
		runCatching { arr["a"] = null }.onSuccess { Assert.fail() }.onFailure { e ->
			Assert.assertTrue(e is InvalidIndexException)
			e as InvalidIndexException
			Assert.assertEquals(arr, e.arr)
			Assert.assertEquals("a", e.value)
		}
	}
}