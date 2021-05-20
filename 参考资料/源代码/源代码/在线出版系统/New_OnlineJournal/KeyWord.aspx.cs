using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace New_OnlineJournal
{
    public partial class KeyWordTest : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            String keyword = TextBox2.Text;
            if (keyword != "" && (article_list.SelectedIndex == 0 || article_list.Items.Count == 0))
            {
                AuthorAritcleDataDataContext aadc = new AuthorAritcleDataDataContext();
                var article_lister = (from t1 in aadc.Article join t2 in aadc.Category on t1.articleid equals t2.articleid where t2.category1.Contains(keyword) || t1.title.Contains(keyword) select t1).Distinct();
                article_list.DataSource = article_lister;
                article_list.DataTextField = "title";
                article_list.DataValueField = "articleid";
                article_list.DataBind();
            }
            else
                TextBox1.Text = "";

            UpdatePanel2.Update();

            if (article_list.Items.Count != 0)
            {
                String nid = article_list.SelectedValue;
                AuthorAritcleDataDataContext naadc = new AuthorAritcleDataDataContext();
                var abstr = from x in naadc.Article where x.articleid == Convert.ToInt32(nid) select x;
                if (abstr.Count() != 0)
                    foreach (var x in abstr)
                        TextBox1.Text = x.@abstract;
            }
            else
            {
                TextBox1.Text = "";
            }

            UpdatePanel3.Update();
            String script = @"$('.selectpicker').selectpicker('refresh');";
            ScriptManager.RegisterClientScriptBlock(UpdatePanel2, typeof(UpdatePanel), "UpdatePanel2", script, true);
        }

        protected void Button2_Click(object sender, EventArgs e)
        {
            String keyword = TextBox2.Text;
            if (keyword != "")
            {
                AuthorAritcleDataDataContext aadc = new AuthorAritcleDataDataContext();
                var article_lister = (from t1 in aadc.Article join t2 in aadc.Category on t1.articleid equals t2.articleid where t2.category1.Contains(keyword) || t1.title.Contains(keyword) select t1).Distinct();
                article_list.DataSource = article_lister;
                article_list.DataTextField = "title";
                article_list.DataValueField = "articleid";
                article_list.DataBind();
            }
            else
                TextBox1.Text = "";

            UpdatePanel2.Update();

            if (article_list.Items.Count != 0)
            {
                String nid = article_list.SelectedValue;
                AuthorAritcleDataDataContext naadc = new AuthorAritcleDataDataContext();
                var abstr = from x in naadc.Article where x.articleid == Convert.ToInt32(nid) select x;
                if (abstr.Count() != 0)
                    foreach (var x in abstr)
                        TextBox1.Text = x.@abstract;
            }
            else
            {
                TextBox1.Text = "";
            }

            UpdatePanel3.Update();
            
        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            const String address = "http://files-1253208676.costj.myqcloud.com/publish/";
            if (article_list.Items.Count == 0 || article_list.SelectedItem.Text == "")
                return;
            else
            {
                String str1 = "<script>window.open('" + address + Convert.ToString(article_list.SelectedItem.Value) + ".docx" + "','_blank')</script>";
                //String str2 = "<script>window.open('" + address + Convert.ToString(article_list.SelectedItem.Value) + ".doc" + "','_blank')</script>";
                Response.Write(str1);
                //Response.Write(str2);
            }
        }

        protected void article_list_SelectedIndexChanged(object sender, EventArgs e)
        {
            String id = article_list.SelectedValue;
            AuthorAritcleDataDataContext aadc = new AuthorAritcleDataDataContext();
            var abstr = from x in aadc.Article where x.articleid == Convert.ToInt32(id) select x;
            if (abstr.Count() != 0)
                foreach (var x in abstr)
                    TextBox1.Text = x.@abstract;

            UpdatePanel3.Update();
        }

        protected void TextBox1_Load(object sender, EventArgs e)
        {
            Console.WriteLine("qe.r");
        }
    }
}