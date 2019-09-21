package com.dev.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.dev.beans.Asset;
import com.dev.beans.AssetAllocation;
import com.dev.beans.AssetStatus;
import com.dev.beans.Employee;
import com.dev.beans.UserMaster;
import com.dev.exceptions.AddAssetException;
import com.dev.exceptions.AddEmployeeException;
import com.dev.exceptions.AssetAllocationException;
import com.dev.exceptions.GetAssetException;
import com.dev.exceptions.LoginException;
import com.dev.exceptions.RaiseAllocationException;
import com.dev.exceptions.RemoveAssetException;
import com.dev.exceptions.StatusException;
import com.dev.exceptions.UpdateAssetException;


@Repository
public class DAOImpl implements DAO {

	@PersistenceUnit
	EntityManagerFactory entityManagerFactory = null;
	UserMaster um = new UserMaster();
/**This function is used for login purpose**/
	public UserMaster login(Integer userid, String password) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			String jpql = "select usertype from UserMaster where userid=:uid and userpassword=:upwd";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("uid", userid);
			query.setParameter("upwd", password);
			String lst = (String) query.getSingleResult();
			if (lst == null) {
				throw new LoginException();
			} else {
				um.setUsertype(lst);
				return um;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		entityManager.close();
		return null;
	}

	/**This function is used for adding the asset**/
	public Asset addAsset(Asset asset) {
		EntityManager entityManager = null;

		EntityTransaction entityTransaction = null;
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("asset_management");
			entityManager = entityManagerFactory.createEntityManager();

			Asset asset1 = entityManager.find(Asset.class, asset.getAssetid());
			if (asset1 == null) {
				entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				entityManager.persist(asset);
				entityTransaction.commit();
				return asset;
			}

			else {
				throw new AddAssetException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		entityManager.close();
		return null;
	}

	/**This function is used for removing the asset**/
	public Asset removeAsset(Integer aid) {
		EntityManager entityManager = null;

		EntityTransaction entityTransaction = null;
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("asset_management");
			entityManager = entityManagerFactory.createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			String jpql = "Delete from Asset where assetid=:assid";
			Asset asset = entityManager.find(Asset.class, aid);
			Query query = entityManager.createQuery(jpql);
			query.setParameter("assid", aid);
			Integer i = query.executeUpdate();
			if (i > 0) {
				entityTransaction.commit();
				return asset;
			} else {

				throw new RemoveAssetException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**This function is used for getting all the asset**/
	public List<Asset> getAllAsset() {
		EntityManager entityManager = null;
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("asset_management");
			entityManager = entityManagerFactory.createEntityManager();
			String jpql = "from Asset";
			TypedQuery<Asset> query = entityManager.createQuery(jpql, Asset.class);
			List<Asset> list = query.getResultList();
			if (!list.isEmpty()) {
				/**for (Asset asset : list) {
					System.out.println("ASSet Id:" + asset.getAssetid());
					System.out.println("ASSET name:" + asset.getAssetname());
					System.out.println("ASSET Description:" + asset.getAssetdes());
					System.out.println("ASSET Quantity:" + asset.getQuantity());
					System.out.println("ASSET Status:" + asset.getStatus());
					System.out.println("*********************");
				}**/
				return list;
			} else {

				throw new GetAssetException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		entityManager.close();
		return null;

	}

	/**This function is used for adding the employees**/
	public Employee addEmployee(Employee employee) {
		EntityManager entityManager = null;

		EntityTransaction entityTransaction = null;
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("asset_management");
			entityManager = entityManagerFactory.createEntityManager();
			Employee employee1 = entityManager.find(Employee.class, employee.getEmpno());
			if (employee1 == null) {
				String query1 = "select deptid from Department ";

				Query query = entityManager.createQuery(query1);
				List<Integer> list = (List<Integer>) query.getResultList();
				for (Integer emp : list) {

					if (employee.getDeptid() == emp) {
						entityTransaction = entityManager.getTransaction();
						entityTransaction.begin();

						entityManager.persist(employee);
						entityTransaction.commit();
						return employee;

					}
				}

				throw new AddEmployeeException();

			} else {

				throw new AddEmployeeException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**This function is used for raising the asset allocation**/
	public AssetAllocation raiseAllocation(AssetAllocation assetallocation) {
		EntityManager entityManager = null;
		EntityManager entityManager1 = null;
		EntityManager entityManager2 = null;

		EntityTransaction entityTransaction = null;
		Integer min = 2000;
		Integer max = 5000;
		Integer rand = (int) ((Math.random() * ((max - min) + 1)) + min);
		assetallocation.setAllocationid(rand);
		try {
			AssetStatus as = new AssetStatus();
			entityManagerFactory = Persistence.createEntityManagerFactory("asset_management");
			entityManager = entityManagerFactory.createEntityManager();
			AssetAllocation assetallocation1 = entityManager.find(AssetAllocation.class,
					assetallocation.getAllocationid());
			if (assetallocation1 == null) {

				entityManager1 = entityManagerFactory.createEntityManager();
				Asset assetcheck = entityManager1.find(Asset.class, assetallocation.getAssetid());
				entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				entityManager.persist(assetallocation);
				as.setAllocationid(assetallocation.getAllocationid());
				as.setStatus("null");
				entityManager.persist(as);

				entityTransaction.commit();
				return assetallocation;

			}
			

			else {

				throw new RaiseAllocationException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		entityManager.close();
		return null;
	}

		/**This function is used for getting all the asset allocation**/
	public List<AssetAllocation> getAllAssetAllocation() {
		EntityManager entityManager = null;

		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("asset_management");
			entityManager = entityManagerFactory.createEntityManager();
			String jpql = "from AssetAllocation";
			Query query = entityManager.createQuery(jpql);
			List<AssetAllocation> list = query.getResultList();
			if (!list.isEmpty()) {
				/**for (AssetAllocation assetallocation : list) {
					System.out.println("Allocation Id:" + assetallocation.getAllocationid());
					System.out.println("ASSET id:" + assetallocation.getAssetid());
					System.out.println("Employee Number:" + assetallocation.getEmpno());
					System.out.println("Allocation Date:" + assetallocation.getAllocationdate());
					System.out.println("Release Date:" + assetallocation.getReleasedate());
					System.out.println("Quantity:" + assetallocation.getQuantity());
					System.out.println("*********************************");
				}**/
				return list;
			} else {

				throw new AssetAllocationException();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		entityManager.close();
		return null;
	}
	/**This function is used for setting the asset status**/
	public AssetStatus setStatus(Integer allocationid, String status) {

		EntityManager entityManager = null;
		AssetStatus assetstatus = new AssetStatus();
		EntityTransaction entityTransaction = null;
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("asset_management");
			entityManager = entityManagerFactory.createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			String jpql = "UPDATE AssetStatus SET status=:asname WHERE allocationid=:allocid";

			Query query = entityManager.createQuery(jpql);
			query.setParameter("asname", status);
			query.setParameter("allocid", allocationid);
			Integer i = query.executeUpdate();
			System.out.println(i);
			if (i > 0) {
				entityTransaction.commit();
				assetstatus.setAllocationid(allocationid);
				assetstatus.setStatus(status);
				return assetstatus;
			} else {

				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**This function is used for viewing the asset status**/
	public AssetStatus viewStatus(Integer allocationid) {
		EntityManager entityManager = null;
		AssetStatus assetstatus = new AssetStatus();

		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("asset_management");
			entityManager = entityManagerFactory.createEntityManager();
			String jpql = "from AssetStatus where allocationid=:allocid";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("allocid", allocationid);
			List<AssetStatus> list = query.getResultList();
			for (AssetStatus astatus : list) {
				System.out.println(astatus.getAllocationid());
				if (astatus.getStatus().equals("null")) {
					assetstatus.setStatus("Status not updated till now");
					assetstatus.setAllocationid(allocationid);
					return assetstatus;
				} else {
					String temp = astatus.getStatus();
					assetstatus.setStatus(temp);
					assetstatus.setAllocationid(allocationid);
					return assetstatus;
				}
			}

			throw new StatusException();
		} catch (Exception e) {

			e.printStackTrace();
		}
		assetstatus.setStatus("Status not updated till now");
		assetstatus.setAllocationid(allocationid);
		return assetstatus;
	}

	@Override
	/**This function is used for updating the asset**/
	public Asset updateAsset(Asset asu) {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("asset_management");
			entityManager = entityManagerFactory.createEntityManager();
			 entityManager.remove(entityManager.find(Asset.class, asu.getAssetid()));
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.persist(asu);
			transaction.commit();
			
		return asu;
	}
		catch (Exception e) {
			e.printStackTrace();
		}
		return asu;
	}
}








