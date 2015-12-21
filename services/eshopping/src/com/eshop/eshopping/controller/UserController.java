/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/

package com.eshop.eshopping.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/
import com.eshop.eshopping.service.ItemorderService;
import com.eshop.eshopping.service.ProductorderService;
import com.eshop.eshopping.service.UserService;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.TypeMismatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.util.WMMultipartUtils;
import com.wavemaker.runtime.util.WMRuntimeUtils;
import com.wordnik.swagger.annotations.*;
import com.eshop.eshopping.*;
import com.eshop.eshopping.service.*;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;

/**
 * Controller object for domain model class User.
 * @see com.eshop.eshopping.User
 */
@RestController(value = "Eshopping.UserController")
@RequestMapping("/eshopping/User")
@Api(description = "Exposes APIs to work with User resource.", value = "UserController")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("eshopping.UserService")
    private UserService userService;

    @Autowired
    @Qualifier("eshopping.ItemorderService")
    private ItemorderService itemorderService;

    @Autowired
    @Qualifier("eshopping.ProductorderService")
    private ProductorderService productorderService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Returns the list of User instances matching the search criteria.")
    public Page<User> findUsers(Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Users list");
        return userService.findAll(queryFilters, pageable);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the list of User instances.")
    public Page<User> getUsers(Pageable pageable) {
        LOGGER.debug("Rendering Users list");
        return userService.findAll(pageable);
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the User instance associated with the given id.")
    public User getUser(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting User with id: {}", id);
        User instance = userService.findById(id);
        LOGGER.debug("User details with id: {}", instance);
        return instance;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes the User instance associated with the given id.")
    public boolean deleteUser(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting User with id: {}", id);
        User deleted = userService.delete(id);
        return deleted != null;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates the User instance associated with the given id.")
    public User editUser(@PathVariable("id") Integer id, @RequestBody User instance) throws EntityNotFoundException {
        LOGGER.debug("Editing User with id: {}", instance.getId());
        instance.setId(id);
        instance = userService.update(instance);
        LOGGER.debug("User details with id: {}", instance);
        return instance;
    }

    @RequestMapping(value = "/{id:.+}/productorders", method = RequestMethod.GET)
    @ApiOperation(value = "Gets the productorders instance associated with the given id.")
    public Page<Productorder> findAssociatedproductorders(Pageable pageable, @PathVariable("id") Integer id) {
        LOGGER.debug("Fetching all associated productorders");
        return productorderService.findAssociatedValues(id, "user", "id", pageable);
    }

    @RequestMapping(value = "/{id:.+}/itemorders", method = RequestMethod.GET)
    @ApiOperation(value = "Gets the itemorders instance associated with the given id.")
    public Page<Itemorder> findAssociateditemorders(Pageable pageable, @PathVariable("id") Integer id) {
        LOGGER.debug("Fetching all associated itemorders");
        return itemorderService.findAssociatedValues(id, "user", "id", pageable);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new User instance.")
    public User createUser(@RequestBody User instance) {
        LOGGER.debug("Create User with information: {}", instance);
        instance = userService.create(instance);
        LOGGER.debug("Created User with information: {}", instance);
        return instance;
    }

    /**
	 * This setter method should only be used by unit tests
	 * 
	 * @param service
	 */
    protected void setUserService(UserService service) {
        this.userService = service;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Returns the total count of User instances.")
    public Long countAllUsers() {
        LOGGER.debug("counting Users");
        Long count = userService.countAll();
        return count;
    }
}
