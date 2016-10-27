package sample.stateservice.serializers;

import java.io.IOException;

import sample.stateservice.vo.CountryInfoResponse;
import akka.util.ByteString;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightbend.lagom.javadsl.api.deser.DeserializationException;
import com.lightbend.lagom.javadsl.api.deser.MessageSerializer;

public class JsonResponseDeserializer
		implements
		MessageSerializer.NegotiatedDeserializer<CountryInfoResponse, ByteString> {
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public CountryInfoResponse deserialize(ByteString bytes)
			throws DeserializationException {
		try {
			return mapper.readValue(bytes.iterator().asInputStream(),
					CountryInfoResponse.class);
		} catch (IOException e) {
			throw new DeserializationException(e);
		}
	}
}