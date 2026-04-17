/*
 * Copyright (c) 2021 EFT Corporation Limited. All Rights Reserved
 *
 * This software may not be copied, distributed, modified or extended in any form without the
 * express written permission of EFT Corporation Limited.
 */

package com.custom_dress.demo.gateway.controller.api.v1;

import com.custom_dress.demo.gateway.controller.api.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1/messages")
public abstract class MessageController extends BaseController
{
}
