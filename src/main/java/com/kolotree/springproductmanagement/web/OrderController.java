package com.kolotree.springproductmanagement.web;

import com.kolotree.springproductmanagement.dto.OrderDto;
import com.kolotree.springproductmanagement.dto.OrderPlacementDto;
import com.kolotree.springproductmanagement.ports.OrderRepository;
import com.kolotree.springproductmanagement.service.OrderPlacementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderRepository orderRepository;
    private final OrderPlacementService orderPlacementService;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderPlacementService orderPlacementService) {
        this.orderRepository = orderRepository;
        this.orderPlacementService = orderPlacementService;
    }

    @PostMapping(value = "/order")
    @ApiOperation(value = "Place an order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderPlacementDto dto) {
        LOGGER.debug("Executing place order {}...", dto);
        var order = orderPlacementService.placeOrder(dto.getBuyersEmail(), dto.getProductIds());
        var response = ResponseEntity.ok(OrderDto.from(order));
        LOGGER.debug("Saving of order {} finished", order);
        return response;
    }

    @GetMapping("/order")
    @ApiOperation(value = "Returns all orders from the given time range")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    public ResponseEntity<List<OrderDto>> getAllFromRange(
            @RequestParam
            @ApiParam(name = "from", value = "Date in format yyyy-MM-dd", required = true)
                    String from,
            @RequestParam
            @ApiParam(name = "to", value = "Date in format yyyy-MM-dd", required = true)
                    String to
    ) {
        LOGGER.debug("Executing getAll orders from range [{}, {}]...", from, to);
        var response = ResponseEntity.ok(orderRepository.getAllOrdersInRange(LocalDate.parse(from), LocalDate.parse(to)).stream().map(OrderDto::from).collect(Collectors.toList()));
        LOGGER.debug("Getting all orders finished");
        return response;
    }
}
