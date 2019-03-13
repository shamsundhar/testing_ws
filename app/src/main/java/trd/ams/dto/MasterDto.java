package trd.ams.dto;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

public class MasterDto {



		//	private String userLoginId;
		private String appName;
		private String imeiNo;
		private String registrationId;
		private String previousTimestamp;
		private String currentTimestamp;
		private String message;
		private boolean imeiAuth;

		private String fromDate;
		private String thruDate;

		private ResponseProductDto createdResponseProductDto;
		private ResponseProductDto updatedResponseProductDto;
		private ResponseProductDto deletedResponseProductDto;

	// ******************  AppDeviceUnitDto ***********************

	private ResponseAppDeviceUnitDto createdResponseAppDeviceUnitDto;
	private ResponseAppDeviceUnitDto updatedResponseAppDeviceUnitDto;
	private ResponseAppDeviceUnitDto deletedResponseAppDeviceUnitDto;

	public ResponseAppDeviceUnitDto getCreatedResponseAppDeviceUnitDto() {
		return createdResponseAppDeviceUnitDto;
	}
	public void setCreatedResponseAppDeviceUnitDto(
			ResponseAppDeviceUnitDto createdResponseAppDeviceUnitDto) {
		this.createdResponseAppDeviceUnitDto = createdResponseAppDeviceUnitDto;
	}
	public ResponseAppDeviceUnitDto getUpdatedResponseAppDeviceUnitDto() {
		return updatedResponseAppDeviceUnitDto;
	}
	public void setUpdatedResponseAppDeviceUnitDto(
			ResponseAppDeviceUnitDto updatedResponseAppDeviceUnitDto) {
		this.updatedResponseAppDeviceUnitDto = updatedResponseAppDeviceUnitDto;
	}
	public ResponseAppDeviceUnitDto getDeletedResponseAppDeviceUnitDto() {
		return deletedResponseAppDeviceUnitDto;
	}
	public void setDeletedResponseAppDeviceUnitDto(
			ResponseAppDeviceUnitDto deletedResponseAppDeviceUnitDto) {
		this.deletedResponseAppDeviceUnitDto = deletedResponseAppDeviceUnitDto;
	}

	//	createdProductDtos;
//	private List<ProductDto> updatedProductDtos;
//	private List<ProductDto> deletedProductDtos;

		private ResponseFacilityDto createdResponseFacilityDto;
		private ResponseFacilityDto updatedResponseFacilityDto;
		private ResponseFacilityDto deletedResponseFacilityDto;

	/*private List<FacilityDto> createdFacilityDtos;
	private List<FacilityDto> updatedFacilityDtos;
	private List<FacilityDto> deletedFacilityDtos;*/

		private ResponseProductCategoryMemberDto createdResponseProductCategoryMemberDto;
		private ResponseProductCategoryMemberDto updatedResponseProductCategoryMemberDto;
		private ResponseProductCategoryMemberDto deletedResponseProductCategoryMemberDto;

	/*private List<ProductCategoryMemberDto> createdProductCategoryMemberDtos;
	private List<ProductCategoryMemberDto> updatedProductCategoryMemberDtos;
	private List<ProductCategoryMemberDto> deletedProductCategoryMemberDtos;*/

		private ResponseScheduleDto createdResponseScheduleDto;
		private ResponseScheduleDto updatedResponseScheduleDto;
		private ResponseScheduleDto deletedResponseScheduleDto;

	/*private List<ScheduleDto> createdScheduleDtos;
	private List<ScheduleDto> updatedScheduleDtos;
	private List<ScheduleDto> deletedScheduleDtos;*/

		private ResponseAssetScheduleAssocDto createdResponseAssetScheduleAssocDto;
		private ResponseAssetScheduleAssocDto updatedResponseAssetScheduleAssocDto;
		private ResponseAssetScheduleAssocDto deletedResponseAssetScheduleAssocDto;

	/*private List<AssetScheduleAssocDto> createdAssetScheduleAssocDtos;
	private List<AssetScheduleAssocDto> updatedAssetScheduleAssocDtos;
	private List<AssetScheduleAssocDto> deletedAssetScheduleAssocDtos;*/

		private ResponseAssetScheduleActivityAssocDto createdResponseAssetScheduleActivityAssocDto;
		private ResponseAssetScheduleActivityAssocDto updatedResponseAssetScheduleActivityAssocDto;
		private ResponseAssetScheduleActivityAssocDto deletedResponseAssetScheduleActivityAssocDto;

	/*private List<AssetScheduleActivityAssocDto> createdAssetScheduleActivityAssocDtos;
	private List<AssetScheduleActivityAssocDto> updatedAssetScheduleActivityAssocDtos;
	private List<AssetScheduleActivityAssocDto> deletedAssetScheduleActivityAssocDtos;*/

		private ResponseMeasureOrActivityListDto createdResponseMeasureOrActivityListDto;
		private ResponseMeasureOrActivityListDto updatedResponseMeasureOrActivityListDto;
		private ResponseMeasureOrActivityListDto deletedResponseMeasureOrActivityListDto;

	/*private List<MeasureOrActivityListDto> createdMeasureOrActivityListDtos;
	private List<MeasureOrActivityListDto> updateMeasureOrActivityListDtos;
	private List<MeasureOrActivityListDto> deletedMeasureOrActivityListDtos;*/


//	added additional parameter facility is required

		private ResponseElementarySectionsDto createdResponseElementarySectionsDto;
		private ResponseElementarySectionsDto updatedResponseElementarySectionsDto;
		private ResponseElementarySectionsDto deletedResponseElementarySectionsDto;

	/*private List<ElementarySectionsDto> createdElementarySectionsDtos;
	private List<ElementarySectionsDto> updatedElementarySectionsDtos;
	private List<ElementarySectionsDto> deletedElementarySectionsDtos;*/

		private ResponsePowerBlocksDto createdResponsePowerBlocksDto;
		private ResponsePowerBlocksDto updatedResponsePowerBlocksDto;
		private ResponsePowerBlocksDto deletedResponsePowerBlocksDto;

	/*private List<PowerBlocksDto> createdPowerBlocksDtos;
	private List<PowerBlocksDto> updatedPowerBlocksDtos;
	private List<PowerBlocksDto> deletedPowerBlocksDtos;*/

		private ResponseAssetMasterDataDto createdResponseAssetMasterDataDto;
		private ResponseAssetMasterDataDto updatedResponseAssetMasterDataDto;
		private ResponseAssetMasterDataDto deletedResponseAssetMasterDataDto;

	/*private List<AssetMasterDataDto> createdAssetMasterDataDtos;
	private List<AssetMasterDataDto> updatedAssetMasterDataDtos;
	private List<AssetMasterDataDto> deletedAssetMasterDataDtos;*/

		private ResponseAssetMonthlyTargetsDto createdResponseAssetMonthlyTargetsDto;
		private ResponseAssetMonthlyTargetsDto updatedResponseAssetMonthlyTargetsDto;
		private ResponseAssetMonthlyTargetsDto deletedResponseAssetMonthlyTargetsDto;

	/*private List<AssetMonthlyTargetsDto> createdAssetMonthlyTargetsDtos;
	private List<AssetMonthlyTargetsDto> updatedAssetMonthlyTargetsDtos;
	private List<AssetMonthlyTargetsDto> deletedAssetMonthlyTargetsDtos;*/

		private ResponseAssetStatusUpdateDto createdResponseAssetStatusUpdateDto;
		private ResponseAssetStatusUpdateDto updatedResponseAssetStatusUpdateDto;
		private ResponseAssetStatusUpdateDto deletedResponseAssetStatusUpdateDto;

	/*private List<AssetStatusUpdateDto> createdAssetStatusUpdateDtos;
	private List<AssetStatusUpdateDto> updatedAssetStatusUpdateDtos;
	private List<AssetStatusUpdateDto> deletedAssetStatusUpdateDtos;*/


		private ResponseUserLoginDto createdResponseUserLoginDto;
		private ResponseUserLoginDto updatedResponseUserLoginDto;
		private ResponseUserLoginDto deletedResponseUserLoginDto;


		private ResponseAssetScheduleGraphDto createdResponseAssetScheduleGraphDto;
		private ResponseAssetScheduleGraphDto updatedResponseAssetScheduleGraphDto;
		private ResponseAssetScheduleGraphDto deletedResponseAssetScheduleGraphDto;


//	ended additional parameter facility is required

//	added for bi directional data sake

		private ResponseAssetsScheduleHistoryDto appToServerCreatedResponseAssetsScheduleHistoryDto;
		private ResponseAssetsScheduleHistoryDto serverToAppCreatedResponseAssetsScheduleHistoryDto;
		private ResponseAssetsScheduleHistoryDto serverToAppUpdatedResponseAssetsScheduleHistoryDto;
		private ResponseAssetsScheduleHistoryDto serverToAppDeletedResponseAssetsScheduleHistoryDto;

		private Map<String, String> serverToAppAssetsScheduleHistoryMap;

	/*private List<AssetsScheduleHistoryDto> appToServerCreatedAssetsScheduleHistoryDtos;
	private List<AssetsScheduleHistoryDto> serverToAppCreatedAssetsScheduleHistoryDtos;
	private List<AssetsScheduleHistoryDto> serverToAppUpdatedAssetsScheduleHistoryDtos;
	private List<AssetsScheduleHistoryDto> serverToAppDeletedAssetsScheduleHistoryDtos;*/

		private ResponseAssetScheduleActivityRecordDto appToServerCreatedResponseAssetScheduleActivityRecordDto;
		private ResponseAssetScheduleActivityRecordDto serverToAppCreatedResponseAssetScheduleActivityRecordDto;
		private ResponseAssetScheduleActivityRecordDto serverToAppUpdatedResponseAssetScheduleActivityRecordDto;
		private ResponseAssetScheduleActivityRecordDto serverToAppDeletedResponseAssetScheduleActivityRecordDto;

		private Map<String, String> serverToAppAssetScheduleActivityRecordMap;
	/*private List<AssetScheduleActivityRecordDto> appToServerCreatedAssetScheduleActivityRecordDtos;
	private List<AssetScheduleActivityRecordDto> serverToAppCreatedAssetScheduleActivityRecordDtos;
	private List<AssetScheduleActivityRecordDto> serverToAppUpdatedAssetScheduleActivityRecordDtos;
	private List<AssetScheduleActivityRecordDto> serverToAppDeletedAssetScheduleActivityRecordDtos;*/

		private ResponseOheLocationDto createdResponseOheLocationDto;
		private ResponseOheLocationDto updatedResponseOheLocationDto;
		private ResponseOheLocationDto deletedResponseOheLocationDto;

		private String facilityId ;
		private String assetType ;
		private String scheduleType ;
		private String date;
		private String assetId;
		private String elementarySection ;
		private byte[] reportProgress;

//	ended for bi directional sake
//	private List<UserLoginDto> userLoginDtos;


		public Map<String, String> getServerToAppAssetScheduleActivityRecordMap() {
			return serverToAppAssetScheduleActivityRecordMap;
		}

		public void setServerToAppAssetScheduleActivityRecordMap(
				Map<String, String> serverToAppAssetScheduleActivityRecordMap) {
			this.serverToAppAssetScheduleActivityRecordMap = serverToAppAssetScheduleActivityRecordMap;
		}

		public Map<String, String> getServerToAppAssetsScheduleHistoryMap() {
			return serverToAppAssetsScheduleHistoryMap;
		}

		public void setServerToAppAssetsScheduleHistoryMap(
				Map<String, String> serverToAppAssetsScheduleHistoryMap) {
			this.serverToAppAssetsScheduleHistoryMap = serverToAppAssetsScheduleHistoryMap;
		}



	/*public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}*/

		public String getPreviousTimestamp() {
			return previousTimestamp;
		}

		public void setPreviousTimestamp(String previousTimestamp) {
			this.previousTimestamp = previousTimestamp;
		}

		public String getCurrentTimestamp() {
			return currentTimestamp;
		}

		public void setCurrentTimestamp(String currentTimestamp) {
			this.currentTimestamp = currentTimestamp;
		}

		public ResponseProductDto getCreatedResponseProductDto() {
			return createdResponseProductDto;
		}

		public void setCreatedResponseProductDto(
				ResponseProductDto createdResponseProductDto) {
			this.createdResponseProductDto = createdResponseProductDto;
		}

		public ResponseProductDto getUpdatedResponseProductDto() {
			return updatedResponseProductDto;
		}

		public void setUpdatedResponseProductDto(
				ResponseProductDto updatedResponseProductDto) {
			this.updatedResponseProductDto = updatedResponseProductDto;
		}

		public ResponseProductDto getDeletedResponseProductDto() {
			return deletedResponseProductDto;
		}

		public void setDeletedResponseProductDto(
				ResponseProductDto deletedResponseProductDto) {
			this.deletedResponseProductDto = deletedResponseProductDto;
		}

		public ResponseFacilityDto getCreatedResponseFacilityDto() {
			return createdResponseFacilityDto;
		}

		public void setCreatedResponseFacilityDto(
				ResponseFacilityDto createdResponseFacilityDto) {
			this.createdResponseFacilityDto = createdResponseFacilityDto;
		}

		public ResponseFacilityDto getUpdatedResponseFacilityDto() {
			return updatedResponseFacilityDto;
		}

		public void setUpdatedResponseFacilityDto(
				ResponseFacilityDto updatedResponseFacilityDto) {
			this.updatedResponseFacilityDto = updatedResponseFacilityDto;
		}

		public ResponseFacilityDto getDeletedResponseFacilityDto() {
			return deletedResponseFacilityDto;
		}

		public void setDeletedResponseFacilityDto(
				ResponseFacilityDto deletedResponseFacilityDto) {
			this.deletedResponseFacilityDto = deletedResponseFacilityDto;
		}

		public ResponseProductCategoryMemberDto getCreatedResponseProductCategoryMemberDto() {
			return createdResponseProductCategoryMemberDto;
		}

		public void setCreatedResponseProductCategoryMemberDto(
				ResponseProductCategoryMemberDto createdResponseProductCategoryMemberDto) {
			this.createdResponseProductCategoryMemberDto = createdResponseProductCategoryMemberDto;
		}

		public ResponseProductCategoryMemberDto getUpdatedResponseProductCategoryMemberDto() {
			return updatedResponseProductCategoryMemberDto;
		}

		public void setUpdatedResponseProductCategoryMemberDto(
				ResponseProductCategoryMemberDto updatedResponseProductCategoryMemberDto) {
			this.updatedResponseProductCategoryMemberDto = updatedResponseProductCategoryMemberDto;
		}

		public ResponseProductCategoryMemberDto getDeletedResponseProductCategoryMemberDto() {
			return deletedResponseProductCategoryMemberDto;
		}

		public void setDeletedResponseProductCategoryMemberDto(
				ResponseProductCategoryMemberDto deletedResponseProductCategoryMemberDto) {
			this.deletedResponseProductCategoryMemberDto = deletedResponseProductCategoryMemberDto;
		}

		public ResponseScheduleDto getCreatedResponseScheduleDto() {
			return createdResponseScheduleDto;
		}

		public void setCreatedResponseScheduleDto(
				ResponseScheduleDto createdResponseScheduleDto) {
			this.createdResponseScheduleDto = createdResponseScheduleDto;
		}

		public ResponseScheduleDto getUpdatedResponseScheduleDto() {
			return updatedResponseScheduleDto;
		}

		public void setUpdatedResponseScheduleDto(
				ResponseScheduleDto updatedResponseScheduleDto) {
			this.updatedResponseScheduleDto = updatedResponseScheduleDto;
		}

		public ResponseScheduleDto getDeletedResponseScheduleDto() {
			return deletedResponseScheduleDto;
		}

		public void setDeletedResponseScheduleDto(
				ResponseScheduleDto deletedResponseScheduleDto) {
			this.deletedResponseScheduleDto = deletedResponseScheduleDto;
		}

		public ResponseAssetScheduleAssocDto getCreatedResponseAssetScheduleAssocDto() {
			return createdResponseAssetScheduleAssocDto;
		}

		public void setCreatedResponseAssetScheduleAssocDto(
				ResponseAssetScheduleAssocDto createdResponseAssetScheduleAssocDto) {
			this.createdResponseAssetScheduleAssocDto = createdResponseAssetScheduleAssocDto;
		}

		public ResponseAssetScheduleAssocDto getUpdatedResponseAssetScheduleAssocDto() {
			return updatedResponseAssetScheduleAssocDto;
		}

		public void setUpdatedResponseAssetScheduleAssocDto(
				ResponseAssetScheduleAssocDto updatedResponseAssetScheduleAssocDto) {
			this.updatedResponseAssetScheduleAssocDto = updatedResponseAssetScheduleAssocDto;
		}

		public ResponseAssetScheduleAssocDto getDeletedResponseAssetScheduleAssocDto() {
			return deletedResponseAssetScheduleAssocDto;
		}

		public void setDeletedResponseAssetScheduleAssocDto(
				ResponseAssetScheduleAssocDto deletedResponseAssetScheduleAssocDto) {
			this.deletedResponseAssetScheduleAssocDto = deletedResponseAssetScheduleAssocDto;
		}

		public ResponseAssetScheduleActivityAssocDto getCreatedResponseAssetScheduleActivityAssocDto() {
			return createdResponseAssetScheduleActivityAssocDto;
		}

		public void setCreatedResponseAssetScheduleActivityAssocDto(
				ResponseAssetScheduleActivityAssocDto createdResponseAssetScheduleActivityAssocDto) {
			this.createdResponseAssetScheduleActivityAssocDto = createdResponseAssetScheduleActivityAssocDto;
		}

		public ResponseAssetScheduleActivityAssocDto getUpdatedResponseAssetScheduleActivityAssocDto() {
			return updatedResponseAssetScheduleActivityAssocDto;
		}

		public void setUpdatedResponseAssetScheduleActivityAssocDto(
				ResponseAssetScheduleActivityAssocDto updatedResponseAssetScheduleActivityAssocDto) {
			this.updatedResponseAssetScheduleActivityAssocDto = updatedResponseAssetScheduleActivityAssocDto;
		}

		public ResponseAssetScheduleActivityAssocDto getDeletedResponseAssetScheduleActivityAssocDto() {
			return deletedResponseAssetScheduleActivityAssocDto;
		}

		public void setDeletedResponseAssetScheduleActivityAssocDto(
				ResponseAssetScheduleActivityAssocDto deletedResponseAssetScheduleActivityAssocDto) {
			this.deletedResponseAssetScheduleActivityAssocDto = deletedResponseAssetScheduleActivityAssocDto;
		}

		public ResponseMeasureOrActivityListDto getCreatedResponseMeasureOrActivityListDto() {
			return createdResponseMeasureOrActivityListDto;
		}

		public void setCreatedResponseMeasureOrActivityListDto(
				ResponseMeasureOrActivityListDto createdResponseMeasureOrActivityListDto) {
			this.createdResponseMeasureOrActivityListDto = createdResponseMeasureOrActivityListDto;
		}

		public ResponseMeasureOrActivityListDto getUpdatedResponseMeasureOrActivityListDto() {
			return updatedResponseMeasureOrActivityListDto;
		}

		public void setUpdatedResponseMeasureOrActivityListDto(
				ResponseMeasureOrActivityListDto updatedResponseMeasureOrActivityListDto) {
			this.updatedResponseMeasureOrActivityListDto = updatedResponseMeasureOrActivityListDto;
		}

		public ResponseMeasureOrActivityListDto getDeletedResponseMeasureOrActivityListDto() {
			return deletedResponseMeasureOrActivityListDto;
		}

		public void setDeletedResponseMeasureOrActivityListDto(
				ResponseMeasureOrActivityListDto deletedResponseMeasureOrActivityListDto) {
			this.deletedResponseMeasureOrActivityListDto = deletedResponseMeasureOrActivityListDto;
		}

		public ResponseElementarySectionsDto getCreatedResponseElementarySectionsDto() {
			return createdResponseElementarySectionsDto;
		}

		public void setCreatedResponseElementarySectionsDto(
				ResponseElementarySectionsDto createdResponseElementarySectionsDto) {
			this.createdResponseElementarySectionsDto = createdResponseElementarySectionsDto;
		}

		public ResponseElementarySectionsDto getUpdatedResponseElementarySectionsDto() {
			return updatedResponseElementarySectionsDto;
		}

		public void setUpdatedResponseElementarySectionsDto(
				ResponseElementarySectionsDto updatedResponseElementarySectionsDto) {
			this.updatedResponseElementarySectionsDto = updatedResponseElementarySectionsDto;
		}

		public ResponseElementarySectionsDto getDeletedResponseElementarySectionsDto() {
			return deletedResponseElementarySectionsDto;
		}

		public void setDeletedResponseElementarySectionsDto(
				ResponseElementarySectionsDto deletedResponseElementarySectionsDto) {
			this.deletedResponseElementarySectionsDto = deletedResponseElementarySectionsDto;
		}

		public ResponsePowerBlocksDto getCreatedResponsePowerBlocksDto() {
			return createdResponsePowerBlocksDto;
		}

		public void setCreatedResponsePowerBlocksDto(
				ResponsePowerBlocksDto createdResponsePowerBlocksDto) {
			this.createdResponsePowerBlocksDto = createdResponsePowerBlocksDto;
		}

		public ResponsePowerBlocksDto getUpdatedResponsePowerBlocksDto() {
			return updatedResponsePowerBlocksDto;
		}

		public void setUpdatedResponsePowerBlocksDto(
				ResponsePowerBlocksDto updatedResponsePowerBlocksDto) {
			this.updatedResponsePowerBlocksDto = updatedResponsePowerBlocksDto;
		}

		public ResponsePowerBlocksDto getDeletedResponsePowerBlocksDto() {
			return deletedResponsePowerBlocksDto;
		}

		public void setDeletedResponsePowerBlocksDto(
				ResponsePowerBlocksDto deletedResponsePowerBlocksDto) {
			this.deletedResponsePowerBlocksDto = deletedResponsePowerBlocksDto;
		}

		public ResponseAssetMasterDataDto getCreatedResponseAssetMasterDataDto() {
			return createdResponseAssetMasterDataDto;
		}

		public void setCreatedResponseAssetMasterDataDto(
				ResponseAssetMasterDataDto createdResponseAssetMasterDataDto) {
			this.createdResponseAssetMasterDataDto = createdResponseAssetMasterDataDto;
		}

		public ResponseAssetMasterDataDto getUpdatedResponseAssetMasterDataDto() {
			return updatedResponseAssetMasterDataDto;
		}

		public void setUpdatedResponseAssetMasterDataDto(
				ResponseAssetMasterDataDto updatedResponseAssetMasterDataDto) {
			this.updatedResponseAssetMasterDataDto = updatedResponseAssetMasterDataDto;
		}

		public ResponseAssetMasterDataDto getDeletedResponseAssetMasterDataDto() {
			return deletedResponseAssetMasterDataDto;
		}

		public void setDeletedResponseAssetMasterDataDto(
				ResponseAssetMasterDataDto deletedResponseAssetMasterDataDto) {
			this.deletedResponseAssetMasterDataDto = deletedResponseAssetMasterDataDto;
		}

		public ResponseAssetMonthlyTargetsDto getCreatedResponseAssetMonthlyTargetsDto() {
			return createdResponseAssetMonthlyTargetsDto;
		}

		public void setCreatedResponseAssetMonthlyTargetsDto(
				ResponseAssetMonthlyTargetsDto createdResponseAssetMonthlyTargetsDto) {
			this.createdResponseAssetMonthlyTargetsDto = createdResponseAssetMonthlyTargetsDto;
		}

		public ResponseAssetMonthlyTargetsDto getUpdatedResponseAssetMonthlyTargetsDto() {
			return updatedResponseAssetMonthlyTargetsDto;
		}

		public void setUpdatedResponseAssetMonthlyTargetsDto(
				ResponseAssetMonthlyTargetsDto updatedResponseAssetMonthlyTargetsDto) {
			this.updatedResponseAssetMonthlyTargetsDto = updatedResponseAssetMonthlyTargetsDto;
		}

		public ResponseAssetMonthlyTargetsDto getDeletedResponseAssetMonthlyTargetsDto() {
			return deletedResponseAssetMonthlyTargetsDto;
		}

		public void setDeletedResponseAssetMonthlyTargetsDto(
				ResponseAssetMonthlyTargetsDto deletedResponseAssetMonthlyTargetsDto) {
			this.deletedResponseAssetMonthlyTargetsDto = deletedResponseAssetMonthlyTargetsDto;
		}

		public ResponseAssetStatusUpdateDto getCreatedResponseAssetStatusUpdateDto() {
			return createdResponseAssetStatusUpdateDto;
		}

		public void setCreatedResponseAssetStatusUpdateDto(
				ResponseAssetStatusUpdateDto createdResponseAssetStatusUpdateDto) {
			this.createdResponseAssetStatusUpdateDto = createdResponseAssetStatusUpdateDto;
		}

		public ResponseAssetStatusUpdateDto getUpdatedResponseAssetStatusUpdateDto() {
			return updatedResponseAssetStatusUpdateDto;
		}

		public void setUpdatedResponseAssetStatusUpdateDto(
				ResponseAssetStatusUpdateDto updatedResponseAssetStatusUpdateDto) {
			this.updatedResponseAssetStatusUpdateDto = updatedResponseAssetStatusUpdateDto;
		}

		public ResponseAssetStatusUpdateDto getDeletedResponseAssetStatusUpdateDto() {
			return deletedResponseAssetStatusUpdateDto;
		}

		public void setDeletedResponseAssetStatusUpdateDto(
				ResponseAssetStatusUpdateDto deletedResponseAssetStatusUpdateDto) {
			this.deletedResponseAssetStatusUpdateDto = deletedResponseAssetStatusUpdateDto;
		}

		public ResponseAssetsScheduleHistoryDto getAppToServerCreatedResponseAssetsScheduleHistoryDto() {
			return appToServerCreatedResponseAssetsScheduleHistoryDto;
		}

		public void setAppToServerCreatedResponseAssetsScheduleHistoryDto(
				ResponseAssetsScheduleHistoryDto appToServerCreatedResponseAssetsScheduleHistoryDto) {
			this.appToServerCreatedResponseAssetsScheduleHistoryDto = appToServerCreatedResponseAssetsScheduleHistoryDto;
		}

		public ResponseAssetsScheduleHistoryDto getServerToAppCreatedResponseAssetsScheduleHistoryDto() {
			return serverToAppCreatedResponseAssetsScheduleHistoryDto;
		}

		public void setServerToAppCreatedResponseAssetsScheduleHistoryDto(
				ResponseAssetsScheduleHistoryDto serverToAppCreatedResponseAssetsScheduleHistoryDto) {
			this.serverToAppCreatedResponseAssetsScheduleHistoryDto = serverToAppCreatedResponseAssetsScheduleHistoryDto;
		}

		public ResponseAssetsScheduleHistoryDto getServerToAppUpdatedResponseAssetsScheduleHistoryDto() {
			return serverToAppUpdatedResponseAssetsScheduleHistoryDto;
		}

		public void setServerToAppUpdatedResponseAssetsScheduleHistoryDto(
				ResponseAssetsScheduleHistoryDto serverToAppUpdatedResponseAssetsScheduleHistoryDto) {
			this.serverToAppUpdatedResponseAssetsScheduleHistoryDto = serverToAppUpdatedResponseAssetsScheduleHistoryDto;
		}

		public ResponseAssetsScheduleHistoryDto getServerToAppDeletedResponseAssetsScheduleHistoryDto() {
			return serverToAppDeletedResponseAssetsScheduleHistoryDto;
		}

		public void setServerToAppDeletedResponseAssetsScheduleHistoryDto(
				ResponseAssetsScheduleHistoryDto serverToAppDeletedResponseAssetsScheduleHistoryDto) {
			this.serverToAppDeletedResponseAssetsScheduleHistoryDto = serverToAppDeletedResponseAssetsScheduleHistoryDto;
		}

		public ResponseAssetScheduleActivityRecordDto getAppToServerCreatedResponseAssetScheduleActivityRecordDto() {
			return appToServerCreatedResponseAssetScheduleActivityRecordDto;
		}

		public void setAppToServerCreatedResponseAssetScheduleActivityRecordDto(
				ResponseAssetScheduleActivityRecordDto appToServerCreatedResponseAssetScheduleActivityRecordDto) {
			this.appToServerCreatedResponseAssetScheduleActivityRecordDto = appToServerCreatedResponseAssetScheduleActivityRecordDto;
		}

		public ResponseAssetScheduleActivityRecordDto getServerToAppCreatedResponseAssetScheduleActivityRecordDto() {
			return serverToAppCreatedResponseAssetScheduleActivityRecordDto;
		}

		public void setServerToAppCreatedResponseAssetScheduleActivityRecordDto(
				ResponseAssetScheduleActivityRecordDto serverToAppCreatedResponseAssetScheduleActivityRecordDto) {
			this.serverToAppCreatedResponseAssetScheduleActivityRecordDto = serverToAppCreatedResponseAssetScheduleActivityRecordDto;
		}

		public ResponseAssetScheduleActivityRecordDto getServerToAppUpdatedResponseAssetScheduleActivityRecordDto() {
			return serverToAppUpdatedResponseAssetScheduleActivityRecordDto;
		}

		public void setServerToAppUpdatedResponseAssetScheduleActivityRecordDto(
				ResponseAssetScheduleActivityRecordDto serverToAppUpdatedResponseAssetScheduleActivityRecordDto) {
			this.serverToAppUpdatedResponseAssetScheduleActivityRecordDto = serverToAppUpdatedResponseAssetScheduleActivityRecordDto;
		}

		public ResponseAssetScheduleActivityRecordDto getServerToAppDeletedResponseAssetScheduleActivityRecordDto() {
			return serverToAppDeletedResponseAssetScheduleActivityRecordDto;
		}

		public void setServerToAppDeletedResponseAssetScheduleActivityRecordDto(
				ResponseAssetScheduleActivityRecordDto serverToAppDeletedResponseAssetScheduleActivityRecordDto) {
			this.serverToAppDeletedResponseAssetScheduleActivityRecordDto = serverToAppDeletedResponseAssetScheduleActivityRecordDto;
		}

		public String getAppName() {
			return appName;
		}

		public String getImeiNo() {
			return imeiNo;
		}

		public String getRegistrationId() {
			return registrationId;
		}

		public void setAppName(String appName) {
			this.appName = appName;
		}

		public void setImeiNo(String imeiNo) {
			this.imeiNo = imeiNo;
		}

		public void setRegistrationId(String registrationId) {
			this.registrationId = registrationId;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isImeiAuth() {
			return imeiAuth;
		}

		public void setImeiAuth(boolean imeiAuth) {
			this.imeiAuth = imeiAuth;
		}

		public ResponseUserLoginDto getCreatedResponseUserLoginDto() {
			return createdResponseUserLoginDto;
		}

		public ResponseUserLoginDto getUpdatedResponseUserLoginDto() {
			return updatedResponseUserLoginDto;
		}

		public ResponseUserLoginDto getDeletedResponseUserLoginDto() {
			return deletedResponseUserLoginDto;
		}

		public void setCreatedResponseUserLoginDto(
				ResponseUserLoginDto createdResponseUserLoginDto) {
			this.createdResponseUserLoginDto = createdResponseUserLoginDto;
		}
		public void setUpdatedResponseUserLoginDto(
				ResponseUserLoginDto updatedResponseUserLoginDto) {
			this.updatedResponseUserLoginDto = updatedResponseUserLoginDto;
		}
		public void setDeletedResponseUserLoginDto(
				ResponseUserLoginDto deletedResponseUserLoginDto) {
			this.deletedResponseUserLoginDto = deletedResponseUserLoginDto;
		}

	public ResponseAssetScheduleGraphDto getCreatedResponseAssetScheduleGraphDto() {
		return createdResponseAssetScheduleGraphDto;
	}
	public void setCreatedResponseAssetScheduleGraphDto(ResponseAssetScheduleGraphDto createdResponseAssetScheduleGraphDto) {
		this.createdResponseAssetScheduleGraphDto = createdResponseAssetScheduleGraphDto;
	}

	public ResponseAssetScheduleGraphDto getUpdatedResponseAssetScheduleGraphDto() {
		return updatedResponseAssetScheduleGraphDto;
	}
	public void setUpdatedResponseAssetScheduleGraphDto(ResponseAssetScheduleGraphDto updatedResponseAssetScheduleGraphDto) {
		this.updatedResponseAssetScheduleGraphDto = updatedResponseAssetScheduleGraphDto;
	}

	public ResponseAssetScheduleGraphDto getDeletedResponseAssetScheduleGraphDto() {
		return deletedResponseAssetScheduleGraphDto;
	}
	public void setDeletedResponseAssetScheduleGraphDto(ResponseAssetScheduleGraphDto deletedResponseAssetScheduleGraphDto) {
		this.deletedResponseAssetScheduleGraphDto = deletedResponseAssetScheduleGraphDto;
	}

	public ResponseOheLocationDto getCreatedResponseOheLocationDto() {
			return createdResponseOheLocationDto;
		}

		public void setCreatedResponseOheLocationDto(
				ResponseOheLocationDto createdResponseOheLocationDto) {
			this.createdResponseOheLocationDto = createdResponseOheLocationDto;
		}

		public ResponseOheLocationDto getUpdatedResponseOheLocationDto() {
			return updatedResponseOheLocationDto;
		}

		public void setUpdatedResponseOheLocationDto(
				ResponseOheLocationDto updatedResponseOheLocationDto) {
			this.updatedResponseOheLocationDto = updatedResponseOheLocationDto;
		}

		public ResponseOheLocationDto getDeletedResponseOheLocationDto() {
			return deletedResponseOheLocationDto;
		}

		public void setDeletedResponseOheLocationDto(
				ResponseOheLocationDto deletedResponseOheLocationDto) {
			this.deletedResponseOheLocationDto = deletedResponseOheLocationDto;
		}

		public String getAssetType() {
			return assetType;
		}

		public void setAssetType(String assetType) {
			this.assetType = assetType;
		}

		public String getFacilityId() {
			return facilityId;
		}

		public void setFacilityId(String facilityId) {
			this.facilityId = facilityId;
		}

		public void setElementarySection(String elementarySection) {
			this.elementarySection = elementarySection;
		}

		public String getElementarySection() {
			return elementarySection;
		}

		public byte[] getReportProgress() {
			return reportProgress;
		}

		public void setReportProgress(byte[] reportProgress) {
			this.reportProgress = reportProgress;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getAssetId() {
			return assetId;
		}

		public void setAssetId(String assetId) {
			this.assetId = assetId;
		}

		public String getScheduleType() {
			return scheduleType;
		}

		public void setScheduleType(String scheduleType) {
			this.scheduleType = scheduleType;
		}

		public String getFromDate() {
			return fromDate;
		}

		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}

		public String getThruDate() {
			return thruDate;
		}

		public void setThruDate(String thruDate) {
			this.thruDate = thruDate;
		}


	/*public List<UserLoginDto> getUserLoginDtos() {
		return userLoginDtos;
	}

	public void setUserLoginDtos(List<UserLoginDto> userLoginDtos) {
		this.userLoginDtos = userLoginDtos;
	}*/

	}


