package com.yqboots.actuator.core.repository;

import com.yqboots.actuator.core.ApplicationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApplicationInfoRepository extends JpaRepository<ApplicationInfo, Long>, JpaSpecificationExecutor<ApplicationInfo> {

}
