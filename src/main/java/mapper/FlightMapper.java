// package mapper;
//
// import java.util.List;
//
// import org.mapstruct.Mapper;
//
// import org.mapstruct.Mapping;
// import org.mapstruct.ReportingPolicy;
// import org.mapstruct.factory.Mappers;
//
// import com.example.pigonair.flight.dto.FlightDto;
// import com.example.pigonair.domain.flight.entity.Flight;
//
// @Mapper(componentModel = "spring")
// public interface FlightMapper {
// 	FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);
//
// 	@Mapping(target = "departureTime", source = "departureTime")
// 	@Mapping(target = "arrivalTime", source = "arrivalTime")
// 	@Mapping(target = "origin", source = "origin")
// 	@Mapping(target = "destination", source = "destination")
// 	FlightDto.FlightResponseDto toFlightResponseDto(Flight flight);
//
// 	List<FlightDto.FlightResponseDto> toFlightResponseDtoList(List<Flight> flights);
// }