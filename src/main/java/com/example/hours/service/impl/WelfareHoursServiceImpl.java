package com.example.hours.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hours.dao.WelfareHoursDao;
import com.example.hours.entity.WelfareHour;
import com.example.hours.service.WelfareHoursService;
import org.springframework.stereotype.Service;

@Service("welfareHours")
public class WelfareHoursServiceImpl extends ServiceImpl<WelfareHoursDao, WelfareHour> implements WelfareHoursService {

}
