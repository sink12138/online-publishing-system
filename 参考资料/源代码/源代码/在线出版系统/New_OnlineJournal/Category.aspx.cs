using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace New_OnlineJournal
{
    public partial class Test2 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            
            AuthorDataDataContext dataContext = new AuthorDataDataContext();
            AuthorAritcleDataDataContext aadc = new AuthorAritcleDataDataContext();
            var a = from x in dataContext.Author select x;
            var category = (from x in aadc.Category select x.category1).Distinct();
            ClientScript.RegisterStartupScript(ClientScript.GetType(), "myscript", "<script>autoclick();</script>");
            if (!this.IsPostBack)
            {
                category_list.DataSource = category;
                category_list.DataBind();
                this.category_list.Items.Insert(0, new ListItem("--请选择--", "-1"));
                String chosen_category = category_list.SelectedItem.Text;
                var article_list22 = from t1 in aadc.Article join t2 in aadc.Category on t1.articleid equals t2.articleid where t2.category1 == chosen_category select t1;
                article_list2.DataSource = article_list22;
                article_list2.DataTextField = "title";
                article_list2.DataValueField = "articleid";
                article_list2.DataBind();
                this.article_list2.Items.Insert(0, new ListItem("--请选择--", "-1"));
                UpdatePanel5.Update();

                if (article_list2.Items.Count != 0 && article_list2.SelectedValue != "-1")
                {
                    String nid2 = article_list2.SelectedValue;
                    var abstr2 = from x in aadc.Article where x.articleid == Convert.ToInt32(nid2) select x;
                    if (abstr2.Count() != 0)
                        foreach (var x in abstr2)
                            TextBox2.Text = x.@abstract;
                    UpdatePanel6.Update();
                }
                else TextBox2.Text = "";
            }
            String script = @"$('.selectpicker').selectpicker('refresh');";
            ScriptManager.RegisterClientScriptBlock(UpdatePanel5, typeof(UpdatePanel), "UpdatePanel5", script, true);
            ScriptManager.RegisterClientScriptBlock(UpdatePanel6, typeof(UpdatePanel), "UpdatePanel6", script, true);
        }

        protected void category_list_SelectedIndexChanged(object sender, EventArgs e)
        {
            String chosen_category = category_list.SelectedItem.Text;
            AuthorAritcleDataDataContext aadc = new AuthorAritcleDataDataContext();
            var article_list = from t1 in aadc.Article join t2 in aadc.Category on t1.articleid equals t2.articleid where t2.category1 == chosen_category select t1;
            article_list2.DataSource = article_list;
            article_list2.DataTextField = "title";
            article_list2.DataValueField = "articleid";
            article_list2.DataBind();
            UpdatePanel5.Update();

            String nid2 = article_list2.SelectedValue;
            if (article_list2.Items.Count == 0 || article_list2.SelectedValue == "-1")
            {
                TextBox2.Text = "";

                return;
            }
            else
            {
                var abstr2 = from x in aadc.Article where x.articleid == Convert.ToInt32(nid2) select x;
                if (abstr2.Count() != 0)
                    foreach (var x in abstr2)
                        TextBox2.Text = x.@abstract;

            }
            UpdatePanel6.Update();
        }

        protected void article_list2_SelectedIndexChanged(object sender, EventArgs e)
        {
            String nid2 = article_list2.SelectedValue;
            AuthorAritcleDataDataContext aadc = new AuthorAritcleDataDataContext();
            var abstr2 = from x in aadc.Article where x.articleid == Convert.ToInt32(nid2) select x;
            if (abstr2.Count() != 0 && article_list2.SelectedValue != "-1" && category_list.SelectedValue != "-1")
                foreach (var x in abstr2)
                    TextBox2.Text = x.@abstract;
            else
                TextBox2.Text = "";
            UpdatePanel6.Update();
        }

        protected void Button2_Click(object sender, EventArgs e)
        {
            const String address = "http://files-1253208676.costj.myqcloud.com/publish/";
            if (article_list2.Items.Count == 0 || article_list2.SelectedItem.Text == "" || article_list2.SelectedValue=="-1")
                return;
            else
            {
                String str1 = "<script>window.open('" + address + Convert.ToString(article_list2.SelectedItem.Value) + ".docx" + "','_blank')</script>";
                //String str2 = "<script>window.open('" + address + Convert.ToString(article_list2.SelectedItem.Value) + ".doc" + "','_blank')</script>";
                Response.Write(str1);
                //Response.Write(str2);
            }
        }
    }
}