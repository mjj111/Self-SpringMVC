package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.request.HttpRequest;
import view.response.HttpResponse;

public class UserListController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void doGet(final HttpRequest request, final HttpResponse response) {
        int wannaSize = Integer.parseInt(request.getParameter("size"));
        String result = wannaSize + "만큼의 유저 정보를 읽기 원합니다.";

        response.sendResponse(result);
    }
}
