
package com.dev.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.beans.Asset;
import com.dev.beans.AssetAllocation;
import com.dev.beans.AssetStatus;
import com.dev.beans.Employee;
import com.dev.beans.UserMaster;
import com.dev.services.Services;
/**@RequestMapping,@RequestMapping are used for creating Spring-based java web application
 * and RESTFUL web services**/
@RestController
@RequestMapping("/assets")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
public class AssetController {
	@Autowired
	Services service;

	/**@PostMapping is used to handle the HTTP POST requests matched with the given URL**/
	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	/**We have used @RequestBody so that it can convert HTTP data into java objects**/
	public UserMaster loginService(@RequestBody UserMaster user) {
		UserMaster usermaster = service.loginService(user.getUserid(), user.getUserpassword());
		if (usermaster != null) {
			return usermaster;
		} else {
			return null;
		}
	}
	
	/**@PostMapping is used to handle the HTTP POST requests matched with the given URL**/
	@PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	/**We have used @RequestBody so that it can convert HTTP data into java objects**/
	public Asset addAsset(@RequestBody Asset asset) {
		Asset assetcheck = service.addAssetService(asset);
		if (assetcheck != null)
			return asset;
		else
			return null;
	}

	/**@PutMapping is used to accept HTTP PUT request containing a request body with JSON **/
	@PutMapping(path = "/updateasset/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	/**We have used @PathVariable to retrieve data from the URL**/
	public Asset updateAsset(@RequestBody Asset asset,@PathVariable("id") int id) {
		System.out.println("Welcome to  eclipse");
		 service.updateAssetService(asset);
		 System.out.println("Exit from eclipse");
		return asset;
	}
	/**@GetMapping handles the HTTP requests matched with given URL expression**/
	@GetMapping(path = "/allasset", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Asset> getAllDetails() {

		return service.getAllAssetService();
	}

	/**@PostMapping is used to handle the HTTP POST requests matched with the given URL**/
	@PostMapping(path = "/addemployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	/**We have used @RequestBody so that it can convert HTTP data into java objects**/
	public Employee addEmployee(@RequestBody Employee emp) {
		Employee employee = service.addEmployeeService(emp);
		if (employee != null) {
			return emp;
		} else {
			return null;
		}
	}
	/**@GetMapping handles the HTTP requests matched with given URL expression**/
	@GetMapping(path = "/allassetallocation", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AssetAllocation> getAllAssetAllocationService() {
		List<AssetAllocation> ls = service.getAllAssetAllocationService();
		if (ls != null) {
			return ls;
		} else
			return null;

	}

	/**@PutMapping is used to accept HTTP PUT request containing a request body with JSON **/
	@PutMapping(path = "/setstatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	/**We have used @RequestBody so that it can convert HTTP data into java objects**/
	public AssetStatus setStatusService(@RequestBody AssetStatus assetstatus) {
		
	AssetStatus assetstatus1=	service.setStatusService(assetstatus.getAllocationid(), assetstatus.getStatus());
		if (assetstatus1 != null)
			return assetstatus1;
		else
			System.out.println("test");
			return null;
	
	}
	
	/**@PostMapping is used to handle the HTTP POST requests matched with the given URL**/
	@PostMapping(path = "/raiserequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	/**We have used @RequestBody so that it can convert HTTP data into java objects**/
	public AssetAllocation serviceRaiseReq(@RequestBody AssetAllocation assetallocation) {
		AssetAllocation asstallocation = service.raiseAllocationService(assetallocation);
		if (asstallocation != null)
			return asstallocation;
		else
			System.out.println("test");
			return null;
	}
	/**@DeleteMapping is used for deleting the resource**/
	@DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	/**We have used @PathVariable to retrieve data from the URL**/
	public Asset removeAsset(@PathVariable("id") int r) {
		Asset assets = service.removeAssetService(r);
		if (assets != null)
			return assets;
		else
			return null;
	}
	
	/**@PostMapping is used to handle the HTTP POST requests matched with the given URL**/
	@PostMapping(path = "/viewstatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	/**We have used @RequestBody so that it can convert HTTP data into java objects**/
	public AssetStatus viewStatusService(@RequestBody AssetStatus assetstatusobject) {
		
		AssetStatus assetstatuscheck= service.viewStatusService(assetstatusobject.getAllocationid());
		if (assetstatuscheck != null)
			return assetstatuscheck;
		else
			return null;
	}

}
