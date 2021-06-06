package com.buaa.ops.Service.Emails;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailFactory {

    private static final ReminderEmail newArticleTmpl = new ReminderEmail(
            "新投稿接收提醒",
            "您有新的待处理文章投稿。",
            "尊敬的编辑 %s，您好！",
            "作者 %s 于 %s 向您投稿了文章《%s》，请您及时登录网站进行处理。"
    );

    public ReminderEmail makeNewArticleEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                newArticleTmpl.getSubject(),
                newArticleTmpl.getTitle(),
                String.format(newArticleTmpl.getGreetings(), receiverName),
                String.format(newArticleTmpl.getBody(), operatorName, getDate(), articleTitle)
        );
    }

    private static final ReminderEmail rejectArticleTmpl = new ReminderEmail(
            "稿件拒收提醒",
            "很抱歉，您的稿件被编辑拒收。",
            "尊敬的作者 %s，您好！",
            "编辑 %s 于 %s 拒收了您投稿的文章《%s》，您可以考虑修改后重新投稿。"
    );

    public ReminderEmail makeRejectArticleEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                rejectArticleTmpl.getSubject(),
                rejectArticleTmpl.getTitle(),
                String.format(rejectArticleTmpl.getGreetings(), receiverName),
                String.format(rejectArticleTmpl.getBody(), operatorName, getDate(), articleTitle)
        );
    }

    private static final ReminderEmail reviewAssignmentTmpl = new ReminderEmail(
            "待审稿件提醒",
            "您有新的待审核稿件。",
            "尊敬的审稿人 %s，您好！",
            "编辑 %s 于 %s 指定了您为文章《%s》的本轮审稿人之一。请您下载附件中的文件，抽空完成审稿工作并登录网站提交审核结果，谢谢。"
    );

    public ReminderEmail makeReviewAssignmentEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                reviewAssignmentTmpl.getSubject(),
                reviewAssignmentTmpl.getTitle(),
                String.format(reviewAssignmentTmpl.getGreetings(), receiverName),
                String.format(reviewAssignmentTmpl.getBody(), operatorName, getDate(), articleTitle)
        );
    }

    private static final ReminderEmail reviewPassTmpl = new ReminderEmail(
            "投稿审核通过提醒",
            "恭喜！您的投稿已通过审核。",
            "尊敬的作者 %s，您好！",
            "您投稿的文章《%s》已于 %s 通过了审核，请您登录网站查看审稿人给出的修改建议。您可以选择确认终稿或提交修改稿。"
    );

    public ReminderEmail makeReviewPassEmail(String receiverName, String articleTitle) {
        return new ReminderEmail(
                reviewPassTmpl.getSubject(),
                reviewPassTmpl.getTitle(),
                String.format(reviewPassTmpl.getGreetings(), receiverName),
                String.format(reviewPassTmpl.getBody(), articleTitle, getDate())
        );
    }

    private static final ReminderEmail reviewFailTmpl = new ReminderEmail(
            "投稿未通过审核提醒",
            "很遗憾，您的投稿未通过审核。",
            "尊敬的作者 %s，您好！",
            "您投稿的文章《%s》未通过审稿人的审核，请您登录网站查看审稿人给出的修改建议。您可以考虑修改后再次提交。"
    );

    public ReminderEmail makeReviewFailEmail(String receiverName, String articleTitle) {
        return new ReminderEmail(
                reviewFailTmpl.getSubject(),
                reviewFailTmpl.getTitle(),
                String.format(reviewFailTmpl.getGreetings(), receiverName),
                String.format(reviewFailTmpl.getBody(), articleTitle)
        );
    }

    private static final ReminderEmail reviewFinishedTmpl = new ReminderEmail(
            "文章审核完成提醒",
            "您分配的审稿人已完成审稿工作。",
            "尊敬的编辑 %s，您好！",
            "您为文章《%s》分配的审稿人已于 %s 完成本轮审稿工作。"
    );

    public ReminderEmail makeReviewFinishedEmail(String receiverName, String articleTitle) {
        return new ReminderEmail(
                reviewFinishedTmpl.getSubject(),
                reviewFinishedTmpl.getTitle(),
                String.format(reviewFinishedTmpl.getGreetings(), receiverName),
                String.format(reviewFinishedTmpl.getBody(), articleTitle, getDate())
        );
    }

    private static final ReminderEmail abortPublishingTmpl = new ReminderEmail(
            "放弃出版流程提醒",
            "一名作者已放弃出版流程。",
            "尊敬的编辑 %s，您好！",
            "作者 %s 已于 %s 决定放弃对文章《%s》的出版，处理流程终止。"
    );

    public ReminderEmail makeAbortPublishingEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                abortPublishingTmpl.getSubject(),
                abortPublishingTmpl.getTitle(),
                String.format(abortPublishingTmpl.getGreetings(), receiverName),
                String.format(abortPublishingTmpl.getBody(), operatorName, getDate(), articleTitle)
        );
    }

    private static final ReminderEmail revisedDraftTmpl = new ReminderEmail(
            "修改版稿件接收提醒",
            "您有新的待处理修改稿件。",
            "尊敬的编辑 %s，您好！",
            "作者 %s 于 %s 提交了文章《%s》的修改版本，请您及时登录网站进行处理。"
    );

    public ReminderEmail makeRevisedDraftEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                revisedDraftTmpl.getSubject(),
                revisedDraftTmpl.getTitle(),
                String.format(revisedDraftTmpl.getGreetings(), receiverName),
                String.format(revisedDraftTmpl.getBody(), operatorName, getDate(), articleTitle)
        );
    }

    private static final ReminderEmail rejectDraftTmpl = new ReminderEmail(
            "修改稿拒收提醒",
            "很抱歉，您的修改稿被编辑拒收。",
            "尊敬的作者 %s，您好！",
            "编辑 %s 于 %s 拒绝了您对文章《%s》的修改，您可以考虑修改后重新提交。"
    );

    public ReminderEmail makeRejectDraftEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                rejectDraftTmpl.getSubject(),
                rejectDraftTmpl.getTitle(),
                String.format(rejectDraftTmpl.getGreetings(), receiverName),
                String.format(rejectDraftTmpl.getBody(), operatorName, getDate(), articleTitle)
        );
    }

    private static final ReminderEmail confirmDraftTmpl = new ReminderEmail(
            "终稿确认提醒",
            "您负责的文章已确认终稿。",
            "尊敬的编辑 %s，您好！",
            "作者 %s 已于 %s 确认不再对文章《%s》进行修改。请您及时开始对该文章的排版编辑工作。"
    );

    public ReminderEmail makeConfirmDraftEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                confirmDraftTmpl.getSubject(),
                confirmDraftTmpl.getTitle(),
                String.format(confirmDraftTmpl.getGreetings(), receiverName),
                String.format(confirmDraftTmpl.getBody(), operatorName, getDate(), articleTitle)
        );
    }

    private static final ReminderEmail articlePublishedTmpl = new ReminderEmail(
            "文章出版提醒",
            "恭喜，您的文章已成功出版！",
            "尊敬的作者 %s，您好！",
            "您的文章《%s》已于 %s 成功出版。您可以登录网站进行查看。"
    );

    public ReminderEmail makeArticlePublishedEmail(String receiverName, String articleTitle) {
        return new ReminderEmail(
                articlePublishedTmpl.getSubject(),
                articlePublishedTmpl.getTitle(),
                String.format(articlePublishedTmpl.getGreetings(), receiverName),
                String.format(articlePublishedTmpl.getBody(), articleTitle, getDate())
        );
    }

    private static final ReminderEmail withdrawArticleTmpl = new ReminderEmail(
            "申请撤稿提醒",
            "一名作者正在申请撤稿。",
            "尊敬的编辑 %s，您好！",
            "作者 %s 于 %s 申请撤除已出版的文章《%s》，请您登录网站进行处理。如有疑问，请与该作者联系。"
    );

    public ReminderEmail makeWithdrawArticleEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                withdrawArticleTmpl.getSubject(),
                withdrawArticleTmpl.getTitle(),
                String.format(withdrawArticleTmpl.getGreetings(), receiverName),
                String.format(withdrawArticleTmpl.getBody(), operatorName, getDate(), articleTitle)
        );
    }

    private static final ReminderEmail acceptWithdrawTmpl = new ReminderEmail(
            "撤稿成功提醒",
            "您的撤稿申请已通过。",
            "尊敬的作者 %s，您好！",
            "编辑 %s 已接受了您撤除文章《%s》的请求。期待您的下一次投稿。"
    );

    public ReminderEmail makeAcceptWithdrawEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                acceptWithdrawTmpl.getSubject(),
                acceptWithdrawTmpl.getTitle(),
                String.format(acceptWithdrawTmpl.getGreetings(), receiverName),
                String.format(acceptWithdrawTmpl.getBody(), operatorName, articleTitle)
        );
    }

    private static final ReminderEmail rejectWithdrawTmpl = new ReminderEmail(
            "撤稿失败提醒",
            "很抱歉，您的撤稿请求已被拒绝。",
            "尊敬的作者 %s，您好！",
            "很抱歉，编辑 %s 拒绝了您撤除文章《%s》的请求。若您仍希望撤稿，请与该编辑联系。"
    );

    public ReminderEmail makeRejectWithdrawEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                rejectWithdrawTmpl.getSubject(),
                rejectWithdrawTmpl.getTitle(),
                String.format(rejectWithdrawTmpl.getGreetings(), receiverName),
                String.format(rejectWithdrawTmpl.getBody(), operatorName, articleTitle)
        );
    }

    private static final ReminderEmail claimArticleTmpl = new ReminderEmail(
            "申请认领文章提醒",
            "您负责的文章有新的认领请求。",
            "尊敬的编辑 %s，您好！",
            "作者 %s 申请将您负责的文章《%s》绑定到自己的账号。请您确认对方身份后登录网站接受请求。"
    );

    public ReminderEmail makeClaimArticleEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                claimArticleTmpl.getSubject(),
                claimArticleTmpl.getTitle(),
                String.format(claimArticleTmpl.getGreetings(), receiverName),
                String.format(claimArticleTmpl.getBody(), operatorName, articleTitle)
        );
    }

    private static final ReminderEmail acceptClaimTmpl = new ReminderEmail(
            "认领文章成功提醒",
            "恭喜，您已成功认领了一篇文章。",
            "尊敬的作者 %s，您好！",
            "编辑 %s 已接受了您对文章《%s》的认领请求。该文章将公开展示在您的个人主页。"
    );

    public ReminderEmail makeAcceptClaimEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                acceptClaimTmpl.getSubject(),
                acceptClaimTmpl.getTitle(),
                String.format(acceptClaimTmpl.getGreetings(), receiverName),
                String.format(acceptClaimTmpl.getBody(), operatorName, articleTitle)
        );
    }

    private static final ReminderEmail rejectClaimTmpl = new ReminderEmail(
            "认领文章失败提醒",
            "很抱歉，您的认领文章请求已被拒绝。",
            "尊敬的作者 %s，您好！",
            "很遗憾，编辑 %s 拒绝了您对文章《%s》的认领请求。若您确认自己是该文章的作者之一，请与该编辑联系并说明身份。"
    );

    public ReminderEmail makeRejectClaimEmail(String receiverName, String operatorName, String articleTitle) {
        return new ReminderEmail(
                rejectClaimTmpl.getSubject(),
                rejectClaimTmpl.getTitle(),
                String.format(rejectClaimTmpl.getGreetings(), receiverName),
                String.format(rejectClaimTmpl.getBody(), operatorName, articleTitle)
        );
    }

    private static final ReminderEmail authorCancelledTmpl = new ReminderEmail(
            "身份注销提醒",
            "您的作者身份已被注销。",
            "尊敬的作者 %s，您好！",
            "您的作者身份已被编辑 %s 注销，如有疑问请与该编辑联系。"
    );

    public ReminderEmail makeAuthorCancelledEmail(String receiverName, String operatorName) {
        return new ReminderEmail(
                authorCancelledTmpl.getSubject(),
                authorCancelledTmpl.getTitle(),
                String.format(authorCancelledTmpl.getGreetings(), receiverName),
                String.format(authorCancelledTmpl.getBody(), operatorName, getDate())
        );
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");

    private String getDate() {
        return sdf.format(new Date());
    }

}
