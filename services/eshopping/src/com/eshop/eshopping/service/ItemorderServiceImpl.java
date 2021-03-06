/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/

package com.eshop.eshopping.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wavemaker.runtime.data.dao.*;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;

import com.eshop.eshopping.*;


/**
 * ServiceImpl object for domain model class Itemorder.
 * @see com.eshop.eshopping.Itemorder
 */
@Service("eshopping.ItemorderService")
public class ItemorderServiceImpl implements ItemorderService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ItemorderServiceImpl.class);

    @Autowired
    @Qualifier("eshopping.ItemorderDao")
    private WMGenericDao<Itemorder, Integer> wmGenericDao;
    public void setWMGenericDao(WMGenericDao<Itemorder, Integer> wmGenericDao){
        this.wmGenericDao = wmGenericDao;
    }
     @Transactional(readOnly = true, value = "eshoppingTransactionManager")
     public Page<Itemorder> findAssociatedValues(Object value, String entityName, String key,  Pageable pageable){
          LOGGER.debug("Fetching all associated");
          return this.wmGenericDao.getAssociatedObjects(value, entityName, key, pageable);
     }

    @Transactional(value = "eshoppingTransactionManager")
    @Override
    public Itemorder create(Itemorder itemorder) {
        LOGGER.debug("Creating a new itemorder with information: {}" , itemorder);
        return this.wmGenericDao.create(itemorder);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "eshoppingTransactionManager")
    @Override
    public Itemorder delete(Integer itemorderId) throws EntityNotFoundException {
        LOGGER.debug("Deleting itemorder with id: {}" , itemorderId);
        Itemorder deleted = this.wmGenericDao.findById(itemorderId);
        if (deleted == null) {
            LOGGER.debug("No itemorder found with id: {}" , itemorderId);
            throw new EntityNotFoundException(String.valueOf(itemorderId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Page<Itemorder> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all itemorders");
        return this.wmGenericDao.search(queryFilters, pageable);
    }
    
    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Page<Itemorder> findAll(Pageable pageable) {
        LOGGER.debug("Finding all itemorders");
        return this.wmGenericDao.search(null, pageable);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Itemorder findById(Integer id) throws EntityNotFoundException {
        LOGGER.debug("Finding itemorder by id: {}" , id);
        Itemorder itemorder=this.wmGenericDao.findById(id);
        if(itemorder==null){
            LOGGER.debug("No itemorder found with id: {}" , id);
            throw new EntityNotFoundException(String.valueOf(id));
        }
        return itemorder;
    }
    @Transactional(rollbackFor = EntityNotFoundException.class, value = "eshoppingTransactionManager")
    @Override
    public Itemorder update(Itemorder updated) throws EntityNotFoundException {
        LOGGER.debug("Updating itemorder with information: {}" , updated);
        this.wmGenericDao.update(updated);
        return this.wmGenericDao.findById((Integer)updated.getId());
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public long countAll() {
        return this.wmGenericDao.count();
    }
}


