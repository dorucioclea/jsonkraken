package net.jemzart.jsonkraken.purifier.errors

import net.jemzart.jsonkraken.exceptions.JsonKrakenException

/**
 * An exception used for failures while transforming a map into a JsonObject.
 *
 * @property[map] the map to be transformed.
 * @property[inner] the inner exception which made the transformation fail.
 * @since 2.0
 */
class MapTransformationException(val map: Map<*,*>, val inner: Throwable):
		JsonKrakenException("an error occurred while transforming a map into a JsonValue")