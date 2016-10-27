package sample.stateservice.serializers;

import akka.util.ByteString;

import com.lightbend.lagom.javadsl.api.deser.*;
import com.lightbend.lagom.javadsl.api.transport.MessageProtocol;
import com.lightbend.lagom.javadsl.api.transport.NotAcceptable;
import com.lightbend.lagom.javadsl.api.transport.UnsupportedMediaType;

import java.util.Arrays;
import java.util.List;

import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import sample.stateservice.vo.CountryInfoResponse;

public class ResponseSerializer implements
		StrictMessageSerializer<CountryInfoResponse> {
	@Override
	public PSequence<MessageProtocol> acceptResponseProtocols() {
		return TreePVector.from(Arrays.asList(new MessageProtocol()
				.withContentType("application/json")));
	}

	@Override
	public NegotiatedSerializer<CountryInfoResponse, ByteString> serializerForRequest() {
		return null;
	}

	@Override
	public NegotiatedDeserializer<CountryInfoResponse, ByteString> deserializer(
			MessageProtocol protocol) throws UnsupportedMediaType {
		if (protocol.contentType().get().equals("application/json")) {
			return new JsonResponseDeserializer();
		} else {
			throw new UnsupportedMediaType(protocol,
					new MessageProtocol().withContentType("text/plain"));
		}
	}

	@Override
	public NegotiatedSerializer<CountryInfoResponse, ByteString> serializerForResponse(
			List<MessageProtocol> acceptedMessageProtocols)
			throws NotAcceptable {
		return null;
	}
}
