/*
 * Copyright (c) 2021 EFT Corporation Limited. All Rights Reserved
 *
 * This software may not be copied, distributed, modified or extended in any form without the
 * express written permission of EFT Corporation Limited.
 */

package com.custom_dress.demo.gateway.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Base controller that creates a request mapping for the base resource path to "/api/v1" for
 * version 1 of the REST API. All {@code @Controller} that extend this class will extend the base
 * resource path
 */
@Controller
@RequestMapping(value = "/api/v1")
public abstract class BaseController
{
    // currently no base logic.
}

