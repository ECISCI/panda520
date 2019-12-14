package com.panda520.mall.common.exception.file;

/**
 *  @描述.文件名大小限制异常类
 *
 * @author X
 */
public class FileSizeLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }
}
