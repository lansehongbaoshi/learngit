package com.chsi.knowledge.file.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.util.ConvertUtil;
import com.chsi.knowledge.vo.UpFileResponseVO;
import com.chsi.knowledge.web.util.WebAppUtil;

public class FileAction extends AjaxAction {

    /**
     * 
     */
    private static final long serialVersionUID = 2704575768618319426L;

    private String upload;
    private String filename;
    private String status;
    private String uploadContentType;
    private String uploadFileName;

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String upfile() throws Exception {
        UpFileResponseVO vo = new UpFileResponseVO();
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        try {
            List<String> validateResult = validateData();
            if (validateResult.size() == 0) {
                String userId = WebAppUtil.getUserId();
                File uploadFile = new File(this.upload);
                if (uploadFile.exists()) {
//                    String lobId = RemoteCallUtil.addFile(uploadFile, this.uploadContentType);
//                    FileInfoData pojo = new FileInfoData();
//                    pojo.setLobId(lobId);
//                    pojo.setContentType(uploadContentType);
//                    pojo.setFileName(uploadFileName);
//                    pojo.setFileSize(uploadFile.length());
//                    pojo.setCreateTime(Calendar.getInstance());
//                    pojo.setCreateUserId(userId);
//                    fileService.save(pojo);
                    byte[] bytes = ConvertUtil.fileToByteArray(uploadFile);
                    String url = cmsServiceClient.saveBlob(this.uploadFileName, this.uploadContentType, bytes);
                    if(!this.uploadContentType.contains("image")) {
                        url += "&attach=true";
                    }
                    vo.setState("SUCCESS");
                    vo.setTitle(this.uploadFileName);
//                    vo.setUrl("/file/"+pojo.getId());
                    vo.setUrl(url);
                    vo.setOriginal(this.uploadFileName);
                } else {
                    vo.setState("文件不存在");
                }
            } else {
                vo.setState("上传文件校验有误");
            }
        } catch (Exception e) {
            vo.setState("异常");
            e.printStackTrace();
        } finally {

        }
        writePlainJSON(vo);
        return NONE;
    }
    
    /*public String getfile() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String uri = request.getRequestURI();
        String fileInfoDataId = uri.substring(uri.lastIndexOf("/")+1);
        if(!ValidatorUtil.isNull(fileInfoDataId)) {
            FileInfoData pojo = fileService.getFileInfoData(fileInfoDataId);
            if(pojo!=null) {
                LobVo lobVo = RemoteCallUtil.getFile(pojo.getLobId());
                byte[] content = lobVo.getContent();
                this.response.setContentType(pojo.getContentType());
                ServletOutputStream sos = this.response.getOutputStream();
                sos.write(content);
                sos.flush();
                sos.close();
            }
        }
        return NONE;
    }*/

    public List<String> validateData() throws Exception {
        List<String> errList = new ArrayList<String>();
        if (ValidatorUtil.isNull(upload) || ValidatorUtil.isNull(uploadContentType) || ValidatorUtil.isNull(uploadFileName)) {
            log.error(String.format("上传图片参数有误:{upload:%s,uploadContentType:%s,fileName:%s}", upload, uploadContentType, uploadFileName));
            errList.add("上传图片参数有误");
            return errList;
        }
        return errList;
    }
}
