package com.reference.api.controller;

import com.reference.api.models.Bottle;
import com.reference.api.models.BottleType;
import com.reference.api.models.Compartment;
import com.reference.api.models.User;
import com.reference.api.repository.BottleRepository;
import com.reference.api.repository.BottleTypeRepository;
import com.reference.api.repository.CompartmentRepository;
import com.reference.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@RestController
@RequestMapping("/api/bottle")
public class BottleController {
    @Autowired
    private BottleRepository bottleRepository;
    @Autowired
    private CompartmentRepository compartmentRepository;
    @Autowired
    private BottleTypeRepository bottleTypeRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new bottle
     *
     * @param bottle
     * @return savedBottle
     */
    @RequestMapping(path = "/",
            method = RequestMethod.POST,
            consumes =  MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a bottle type")
    public Bottle add(@RequestBody Bottle bottle) {
        Compartment c = compartmentRepository.findOne(bottle.getCompartment().getId());
        BottleType bt = bottleTypeRepository.findOne(bottle.getType().getId());
        User u = userRepository.findOne(bottle.getOwner().getId());
        Bottle savedBottle =  bottleRepository.save(new Bottle(u, bt, c, bottle.getNbBottles(), bottle.getPhotoUrl()));
        compartmentRepository.save(c);
        return savedBottle;
    }

    /***
     * Get a bottle by id
     */
    @RequestMapping(path="/{bottleID}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get a bottle by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Bottle.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")})
    public ResponseEntity<Bottle> getBottle(@PathVariable("bottleID") Long id) {
        Bottle bottle = bottleRepository.findOne(id);

        if(bottle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(bottle);
        }
    }

    /**
     * Update an existing bottle
     *
     * @param bottle
     * @return savedBottle
     */
    @RequestMapping(path = "/",
            method = RequestMethod.PUT,
            consumes =  MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update an existing bottle")
    public Bottle update(@RequestBody Bottle bottle) {
        Bottle b = bottleRepository.findOne(bottle.getId());

        b.id(bottle.getId());
        b.type(bottle.getType());
        b.compartment(bottle.getCompartment());
        b.setNbBottles(bottle.getNbBottles());
        b.setPhotoUrl(bottle.getPhotoUrl());

        return bottleRepository.save(b);
    }

    /**
     * Deletes bottle by id
     * @param id
     */
    @RequestMapping(path = "/{id}",
            method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a bottle")
    public void delete(@PathVariable Long id) {
        bottleRepository.delete(id);
    }
}
